package com.RainCarnation;

import com.github.sh0nk.matplotlib4j.Plot;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Arrays;


public class NeuralNetworkExtrapolation extends NeuralNetwork<Float, Float> {
    private float trainingNorm;
    private int lengthWindow;
    private float accuracy;
    private ArrayList<Float> plotResult;
    private Float[] lastWindow;
    private Float[] lastWindowX;
    private Plot plot;

    private void construct(float trainingNorm_, int lengthWindow_, float accuracy_, OutputStream out) throws NeuralException {
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

    public NeuralNetworkExtrapolation(float trainingNorm_, int lengthWindow_, float accuracy_, OutputStream out) throws NeuralException {
        construct(trainingNorm_, lengthWindow_, accuracy_, out);
    }

    public NeuralNetworkExtrapolation(float trainingNorm_, int lengthWindow_, float accuracy_) throws NeuralException {
        construct(trainingNorm_, lengthWindow_, accuracy_, System.out);
    }

    @Override
    public void fit(Float[] matrix, Float[] result) throws Exception {
        if (matrix.length <= 0) {
            throw new NeuralException("Input data is clear");
        }

        if (matrix.length != result.length) {
            throw new NeuralException("Invalid input data");
        }

        if (matrix.length <= lengthWindow) {
            throw new NeuralException("Invalid input data");
        }

        lastWindow = Arrays.copyOfRange(result, result.length - lengthWindow, result.length);
        lastWindowX = Arrays.copyOfRange(matrix, matrix.length - lengthWindow, matrix.length);

        weights = new float[lengthWindow + 1];

        float net, epsilon, delta;
        int era = 0;

        resultWriter.write("################   Neural Network Training   ################\n");
        do {
            epsilon = 0;
            era++;
            resultWriter.write("Era #" + era);
            for (int i = 0; i < result.length - weights.length; ++i) {
                net = net(result, i);
                delta = result[weights.length + i] - net;
                for (int j = 1; j < weights.length; ++j) {
                    weights[j] += getCorrectionWeight(delta, result[i + j - 1]);
                }
            }

            for (int i = 0; i < result.length - weights.length; ++i) {
                net = net(result, i);
                delta = result[weights.length + i] - net;
                epsilon += (delta * delta);
            }
            plotResult.add(epsilon);
            resultWriter.write("       Weight Coefficients: (");

            for (int i = 0; i < weights.length - 1; ++i) {
                resultWriter.write(weights[i] + ", ");
            }

            resultWriter.write(weights[weights.length - 1] + ")\n");

        } while ((float)Math.sqrt(epsilon) > accuracy);
        System.out.println(era);
    }

    @Override
    public Float getResult(Float input) throws NeuralException {
        float net = 0;
        LinkedList<Float> windowList = new LinkedList<>();
        Collections.addAll(windowList, lastWindow);
        for (int i = 0; i < getTurnToResult(input); ++i) {
            net = netList(windowList);
            windowList.remove(0);
            windowList.add(net);
        }
        return net;
    }

    public void addGraphicToPlot(Float[] X, Float[] Y) {
        plot.plot().add(Arrays.asList(X), Arrays.asList(Y));
    }

    public void showExtrapolation(Float finishPlot) throws Exception {
        float net;
        final float turn = lastWindowX[lastWindowX.length - 1] - lastWindowX[lastWindowX.length - 2];
        LinkedList<Float> windowList = new LinkedList<>();
        ArrayList<Float> X = new ArrayList<>();
        ArrayList<Float> Y = new ArrayList<>();
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

    private float net(Float[] variables, int windowStart) {
        float result = 0;
        for (int i = 1; i < weights.length; ++i) {
            result += (weights[i] * variables[windowStart + i]);
        }
        return result + weights[0];
    }

    private float netList(LinkedList<Float> variables) {
        float result = 0;
        for (int i = 1; i < weights.length; ++i) {
            result += (weights[i] * variables.get(i - 1));
        }

        return result + weights[0];
    }

    private float getCorrectionWeight(float delta, float variable) {
        return trainingNorm * delta * variable;
    }

    private int getTurnToResult(float input) throws NeuralException {
        if (input < lastWindowX[lastWindowX.length - 1]) {
            throw new NeuralException("Invalid input");
        }
        float turn = lastWindowX[lastWindowX.length - 1] - lastWindowX[lastWindowX.length - 2];

        return (int)((input - lastWindowX[lastWindowX.length - 1]) / turn);
    }
}
