package com.RainCarnation;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.List;

import com.github.sh0nk.matplotlib4j.Plot;

public abstract class NeuralNetworkBoolean extends NeuralNetwork<Boolean[][], Boolean[], Boolean[], Boolean> {
    protected double trainingNorm;

    private Boolean[] resultVector;
    private List<Integer> numberErrors;

    @Override
    public void fit(Boolean[][] matrix, Boolean[] result) throws Exception {
        if (matrix.length <= 0 || matrix[0].length <= 0) {
            throw new NeuralException("Input data is clear");
        }

        if (matrix.length != result.length) {
            throw new NeuralException("Invalid input data");
        }

        weights = new double[matrix[0].length + 1];
        resultVector = new Boolean[result.length];
        numberErrors = new ArrayList<>();


        int errorCount, era = 0, fNetwork;
        double delta, net;

        do {
            era++;
            resultWriter.write("Era #" + era + "      ");

            errorCount = 0;
            for (int i = 0; i < matrix.length; ++i) {
                net = net(matrix[i]);
                fNetwork = fNet(net);
                delta = (result[i] ? 1 : 0) - fNetwork;
                resultVector[i] = fNetwork == 1;
                if (delta != 0) {
                    errorCount++;
                    weights[0] += getCorrectionWeight(true, delta, net);
                    for (int j = 1; j < weights.length; j++) {
                        weights[j] += getCorrectionWeight(matrix[i][j - 1], delta, net);
                    }
                }
            }
            resultWriter.write("Result vector: ");
            for (Boolean value : resultVector) {
                resultWriter.write(value ? "1" : "0");
            }
            resultWriter.write("      Number of error: " + errorCount);
            resultWriter.write("      Weights: (");
            for (int i = 0; i < weights.length - 1; ++i) {
                resultWriter.write(weights[i] + " ");
            }
            resultWriter.write(weights[weights.length - 1] + ")\n");

            numberErrors.add(errorCount);
        } while (errorCount != 0);
    }

    @Override
    public Boolean getResult(Boolean[] input) throws NeuralException {
        if (input.length != weights.length - 1) {
            throw new NeuralException("Invalid variables set");
        }
        return fNet(net(input)) == 1;
    }

    @Override
    public void showFitGraphics() throws Exception {
        Plot plot = Plot.create();

        plot.plot().add(numberErrors);
        plot.xlabel("K (number of eras)");
        plot.ylabel("E(k) (number of errors in era)");
        plot.show();
    }


    protected double net(Boolean[] variables) {
        double result = weights[0];
        for (int i = 1; i < weights.length; ++i) {
            result += (variables[i - 1] ? 1 : 0) * weights[i];
        }
        return result;
    }

    protected void construct(double trainingNorm_, OutputStream out) {
        trainingNorm = trainingNorm_;
        weights = null;

        resultWriter = new BufferedWriter(new OutputStreamWriter(out));
        resultVector = null;
    }

    protected abstract int fNet(double net);

    protected abstract double getCorrectionWeight(boolean variable, double d, double net);

    public static Boolean[][] getStandardMatrix(int numberVariables) {
        int levelCounter = 0;
        int levelController = (int)Math.pow(2, numberVariables - 1);
        final int maxVectors = levelController * 2;
        Boolean[][] result = new Boolean[maxVectors][numberVariables];
        boolean setter = false;

        for (int i = 0; i < numberVariables; ++i) {
            for (int j = 0; j < maxVectors; ++j) {
                if (levelController == levelCounter) {
                    setter ^= true;
                    levelCounter = 0;
                }
                result[j][i] = setter;
                levelCounter++;
            }
            levelController >>= 1;
            levelCounter = 0;
            setter = false;
        }

        return result;
    }
}
