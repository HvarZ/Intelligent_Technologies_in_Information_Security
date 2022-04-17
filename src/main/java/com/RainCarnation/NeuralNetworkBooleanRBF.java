package com.RainCarnation;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class NeuralNetworkBooleanRBF extends NeuralNetworkBoolean {
    protected Boolean[] resultBooleanFunction;

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
        resultVector = new Boolean[standardMatrix.length];
        numberErrors = new ArrayList<>();

        double[] X = new double[C.length];
        int fNet, fNetFull;


        weights = new double[C.length + 1];
        int sumError, delta, era = 0;

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
                resultVector[j] = fNetFull == 1;
                sumError += Math.abs((resultBooleanFunction[j] ? 1 : 0) - fNetFull);
                Arrays.fill(X, 0);
            }

            resultWriter.write("Era #" + (era++) + "\t(");
            for (int i = 0; i < weights.length - 1; ++i) {
                resultWriter.write(weights[i] + ", ");
            }

            resultWriter.write(weights[weights.length - 1] + ")\t Result vector: (");


            for (int i = 0; i < resultVector.length - 1; ++i) {
                resultWriter.write(resultVector[i] ? "1" : "0");
            }
            resultWriter.write(resultVector[resultVector.length - 1] ? "1)\t" : "0)\t");

            resultWriter.write("Sum error:" + sumError + "\n");
            numberErrors.add(sumError);

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
    protected abstract int fNet(double net);

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
