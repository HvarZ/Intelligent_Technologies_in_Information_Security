package com.RainCarnation;

import com.github.sh0nk.matplotlib4j.Plot;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Arrays;


public final class NeuralNetworkExtrapolation extends NeuralNetwork<Double[], Double[], Double, Double> {
    private double trainingNorm;
    private int lengthWindow;
    private double accuracy;
    private ArrayList<Double> plotResult;
    private Double[] lastWindow;
    private Double[] lastWindowX;
    private Plot plot;

    private void construct(double trainingNorm_, int lengthWindow_, double accuracy_, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        if (lengthWindow_ <= 1) {
            throw new NeuralException("Invalid length of window");
        }

        this.trainingNorm = trainingNorm_;
        this.lengthWindow = lengthWindow_;
        this.accuracy = accuracy_;
        weights = null;

        resultWriter = new BufferedWriter(new OutputStreamWriter(out));
        plotResult = new ArrayList<>();

        plot = Plot.create();
    }

    public NeuralNetworkExtrapolation(double trainingNorm_, int lengthWindow_, double accuracy_, OutputStream out) throws NeuralException {
        construct(trainingNorm_, lengthWindow_, accuracy_, out);
    }

    public NeuralNetworkExtrapolation(double trainingNorm_, int lengthWindow_, double accuracy_) throws NeuralException {
        construct(trainingNorm_, lengthWindow_, accuracy_, System.out);
    }

    @Override
    public void fit(Double[] matrix, Double[] result) throws Exception {
        if (matrix.length <= 0) {
            throw new NeuralException("Fit: Input data is clear");
        }

        if (matrix.length != result.length) {
            throw new NeuralException("Fit: Invalid input data");
        }

        if (matrix.length <= lengthWindow) {
            throw new NeuralException("Fit: Invalid input data");
        }

        lastWindow = Arrays.copyOfRange(result, result.length - lengthWindow, result.length);
        lastWindowX = Arrays.copyOfRange(matrix, matrix.length - lengthWindow, matrix.length);

        weights = new double[lengthWindow + 1];
        double sum = 0;

        for (var res : result) {
            sum += res;
        }

        //weights[0] = sum / result.length;

        double net, epsilon, delta;
        int era = 0;

        resultWriter.write("################   Neural Network Training   ################\n");
        do {
            epsilon = 0;
            era++;
            resultWriter.write("Era #" + era);
            for (int i = 0; i < result.length - lengthWindow; ++i) {
                net = net(result, i);
                delta = result[lengthWindow + i] - net;
                epsilon += (delta * delta);
                //weights[0] += getCorrectionWeight(delta, 1);
                for (int j = 1; j < weights.length; ++j) {
                    weights[j] += getCorrectionWeight(delta, result[i + j - 1]);
                }
            }


            plotResult.add(epsilon);
            resultWriter.write("       Weight Coefficients: (");

            for (int i = 0; i < lengthWindow; ++i) {
                resultWriter.write(weights[i] + ", ");
            }

            resultWriter.write(weights[lengthWindow] + ")\n");

        } while (Math.sqrt(epsilon) > accuracy);
        System.out.println(era);
    }

    @Override
    public Double getResult(Double input) throws NeuralException {
        double net = 0;
        LinkedList<Double> windowList = new LinkedList<>();
        Collections.addAll(windowList, lastWindow);
        for (int i = 0; i < getTurnToResult(input); ++i) {
            net = netList(windowList);
            windowList.remove(0);
            windowList.add(net);
        }
        return net;
    }

    public void addGraphicToPlot(Double[] X, Double[] Y) {
        plot.plot().add(Arrays.asList(X), Arrays.asList(Y));
    }

    public void showExtrapolation(double finishPlot) throws Exception {
        double net;
        final double turn = lastWindowX[lastWindowX.length - 1] - lastWindowX[lastWindowX.length - 2];
        LinkedList<Double> windowList = new LinkedList<>();
        ArrayList<Double> X = new ArrayList<>();
        ArrayList<Double> Y = new ArrayList<>();
        X.add(lastWindowX[lastWindowX.length - 1]);
        Y.add(lastWindow[lastWindow.length - 1]);

        Collections.addAll(windowList, lastWindow);
        for (int i = 0; i < getTurnToResult(finishPlot); ++i) {
            X.add((i + 1) * turn + lastWindowX[lastWindowX.length - 1]);
            net = netList(windowList);
            windowList.remove(0);
            windowList.add(net);
            Y.add(net);
        }

        plot.plot().add(X, Y);
        plot.show();
    }

    @Override
    public void showFitGraphics() throws Exception {
        Plot plot = Plot.create();
        plot.plot().add(plotResult);
        plot.show();
    }

    private double net(Double[] variables, int windowStart) {
        double result = 0;
        for (int i = 1; i < weights.length; ++i) {
            result += (weights[i] * variables[windowStart + i - 1]);
        }
        return result + weights[0];
    }

    private double netList(LinkedList<Double> variables) {
        double result = 0;
        for (int i = 1; i < weights.length; ++i) {
            result += (weights[i] * variables.get(i - 1));
        }

        return result + weights[0];
    }

    private double getCorrectionWeight(double delta, double variable) {
        return trainingNorm * delta * variable;
    }

    private int getTurnToResult(double input) throws NeuralException {
        if (input < lastWindowX[lastWindowX.length - 1]) {
            throw new NeuralException("GetTurnToResult: Invalid input");
        }
        double turn = lastWindowX[lastWindowX.length - 1] - lastWindowX[lastWindowX.length - 2];

        return (int)((input - lastWindowX[lastWindowX.length - 1]) / turn);
    }
}