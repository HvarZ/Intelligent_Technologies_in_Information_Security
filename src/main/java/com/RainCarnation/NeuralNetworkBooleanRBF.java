package com.RainCarnation;

import java.io.OutputStream;
import java.util.Arrays;

public class NeuralNetworkBooleanRBF extends NeuralNetworkBoolean {
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


        Boolean[][] standardMatrix = getStandardMatrix(matrix[0].length);
        Boolean[][] C = getMinResultFunction(standardMatrix, resultBooleanFunction);
        double[] X = new double[C.length];
        double net = 0;
        int fNet;

        weights = new double[C.length + 1];
        int sumError, delta;

        do {
            sumError = 0;
            for (int i = 0; i < matrix.length; ++i) {
                Arrays.fill(X, 0);
                for (int j = 0; j < C.length; ++j) {
                    for (int k = 0; k < C[0].length; ++k) {
                        X[j] += Math.pow(((matrix[i][k] ? 1 : 0) - (C[j][k] ? 1 : 0)), 2);
                    }
                    X[j] = Math.exp(-(X[j]));

                    net += weights[j + 1] * X[j];
                }
                net += weights[0];
                fNet = net >= 0 ? 1 : 0;

                delta = (result[i] ? 1 : 0) - fNet;
                sumError += Math.abs(delta);

                for (int j = 0; j < C.length; ++j) {
                    weights[j + 1] += getCorrectionWeight(true, delta, X[j]);
                }
                weights[0] += getCorrectionWeight(true, delta, 1);
            }

            System.out.println(sumError);
        } while (sumError != 0);

    }

    @Override
    public Boolean getResult(Boolean[] input) {

        return true;
    }

    @Override
    protected int fNet(double net) {
        return net >= 0 ? 1 : 0;
    }

    @Override
    protected double getCorrectionWeight(boolean variable, double d, double net) {
        return trainingNorm * d * net;
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
