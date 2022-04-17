package com.RainCarnation;

import java.io.OutputStream;
import java.util.Arrays;

public final class NeuralNetworkBooleanRBF extends NeuralNetworkBoolean {
    Boolean[] resultBooleanFunction;

    public NeuralNetworkBooleanRBF(double trainingNorm_, Boolean[] result, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        resultBooleanFunction = result;
        construct(trainingNorm_, out);
    }

    public NeuralNetworkBooleanRBF(double trainingNorm, Boolean[] result) throws NeuralException {
        this(trainingNorm, result, System.out);
    }

    @Override
    public void fit(Boolean[][] matrix, Boolean[] result) throws Exception {
        if (matrix.length <= 0 || matrix[0].length <= 0) {
            throw new NeuralException("Input data is clear");
        }

        if (matrix.length != result.length) {
            throw new NeuralException("Invalid input data");
        }

        final Boolean[][] standardMatrix = getStandardMatrix(matrix[0].length);
        final Boolean[][] C = getMinResultFunction(standardMatrix, resultBooleanFunction);
        double[] X = new double[C.length];
        int fNet, fNetFull;


        weights = new double[C.length + 1];
        int sumError, delta;

        do {
            sumError = 0;
            for (int i = 0; i < matrix.length; ++i) {
                fNet = directPassage(matrix[i], C, X) ? 1 : 0;
                delta = (result[i] ? 1 : 0) - fNet;

                for (int j = 0; j < C.length; ++j) {
                    weights[j + 1] += getCorrectionWeight(true, delta, X[j]);
                }
                weights[0] += getCorrectionWeight(true, delta, 1);
                Arrays.fill(X, 0);
            }

            for (int j = 0; j < standardMatrix.length; ++j) {
                fNetFull = directPassage(standardMatrix[j], C, X) ? 1 : 0;
                sumError += Math.abs((resultBooleanFunction[j] ? 1 : 0) - fNetFull);
                Arrays.fill(X, 0);
            }

            System.out.println(sumError);
        } while (sumError != 0);
    }



    @Override
    public Boolean getResult(Boolean[] input) {
        Boolean[][] standardMatrix = getStandardMatrix(input.length);
        Boolean[][] C = getMinResultFunction(standardMatrix, resultBooleanFunction);
        double[] X = new double[C.length];

        return directPassage(input, C, X);
    }

    @Override
    protected int fNet(double net) {
        // return (int) Math.round(0.5 + 0.5 * Math.tanh(net));
        return net >= 0 ? 1 : 0;
    }

    @Override
    protected double getCorrectionWeight(boolean variable, double d, double net) {
        return trainingNorm * d * net;
    }

    private Boolean directPassage(final Boolean[] input, final Boolean[][] C, double[] X) {
        double net = 0;

        for (int j = 0; j < C.length; ++j) {
            for (int k = 0; k < C[0].length; ++k) {
                X[j] += Math.pow(((input[k] ? 1 : 0) - (C[j][k] ? 1 : 0)), 2);
            }
            X[j] = Math.exp(-(X[j]));

            net += weights[j + 1] * X[j];
        }
        net += weights[0];

        return fNet(net) == 1;
    }


    private Boolean[][] getMinResultFunction(Boolean[][] matrix, Boolean[] results) {
        int zeros = 0;
        int units = 0;
        int counterAdd = 0;
        boolean moreUnits;
        for (Boolean result : results) {
            if (result) {
                units++;
            } else {
                zeros++;
            }
        }
        moreUnits = units > zeros;
        Boolean[][] result = new Boolean[Math.min(units, zeros)][matrix[0].length];

        for (int i = 0; i < matrix.length; ++i) {
            if (results[i] != moreUnits) {
                result[counterAdd] = matrix[i];
                counterAdd++;
            }
        }

        return result;
    }
}
