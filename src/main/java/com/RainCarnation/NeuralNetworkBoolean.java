package com.RainCarnation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.github.sh0nk.matplotlib4j.Plot;

public abstract class NeuralNetworkBoolean extends NeuralNetwork<Boolean[][], Boolean, Boolean[], Boolean> {
    protected double[] weights;
    protected double trainingNorm;
    protected List<Integer> numberErrors;

    protected Boolean[] resultVector;

    @Override
    public void fit(Boolean[][] matrix, Boolean... result) throws Exception {
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
            era++;
            safePrint(era, errorCount);
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
        int levelController = (int) Math.pow(2, numberVariables - 1);
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

    protected void safePrint(final int era, final int errorCount) throws IOException {
        resultWriter.write("Era #" + era + "\t(");
        for (int i = 0; i < weights.length - 1; ++i) {
            resultWriter.write(new DecimalFormat("#0.000").format(weights[i]).replace(',', '.') + ", ");
        }

        resultWriter.write(new DecimalFormat("#0.000").format(weights[weights.length - 1]).replace(',', '.') + ")\t Result vector: (");


        for (int i = 0; i < resultVector.length - 1; ++i) {
            resultWriter.write(resultVector[i] ? "1" : "0");
        }
        resultWriter.write(resultVector[resultVector.length - 1] ? "1)\t" : "0)\t");

        resultWriter.write("Sum error:" + errorCount + "\n");
    }

    static public Boolean[] convertStringVectorToArray(String vector) throws NeuralException {
        if (!vector.matches("^[0|1]*$")) {
            throw new NeuralException("InvalidVector");
        }
        Boolean[] result = new Boolean[vector.length()];

        for (int i = 0; i < result.length; ++i) {
            result[i] = vector.charAt(i) == '1';
        }

        return result;
    }

    static public Boolean[][] convertStringVectorsToMatrix(String vector) throws NeuralException {
        int index = vector.indexOf(','), numberTerminators = 1;
        if (index == - 1) {
            index = vector.length();
        }
        for (char ch : vector.toCharArray()) {
            if (ch == ',') {
                numberTerminators++;
            }
        }
        String[] vectors = vector.split(",");
        Boolean[][] result = new Boolean[numberTerminators][index];
        for (int i = 0; i < result.length; ++i) {
            result[i] = convertStringVectorToArray(vectors[i]);
        }

        return result;
    }

}
