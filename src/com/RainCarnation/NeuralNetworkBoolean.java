package com.RainCarnation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class NeuralNetworkBoolean extends NeuralNetwork<Boolean, Boolean>  {
    private final float trainingNorm;
    private final boolean isLogistic;
    private final BufferedWriter resultWriter;
    private Boolean[] resultVector;

    public NeuralNetworkBoolean(float trainingNorm_, boolean isLogistic_, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        trainingNorm = trainingNorm_;
        isLogistic = isLogistic_;
        weights = null;

        resultWriter = new BufferedWriter(new OutputStreamWriter(out));
        resultVector = null;
    }

    public NeuralNetworkBoolean(float trainingNorm, boolean isLogistic_) throws NeuralException {
        this(trainingNorm, isLogistic_, System.out);
    }

    public NeuralNetworkBoolean(float trainingNorm) throws NeuralException {
        this(trainingNorm, false, System.out);
    }


    @Override
    public void fit(Boolean[][] matrix, Boolean[] result) throws Exception {
        if (matrix.length <= 0 || matrix[0].length <= 0) {
            throw new NeuralException("Input data is clear");
        }

        if (matrix.length != result.length) {
            throw  new NeuralException("Invalid input data");
        }

        weights = new float[matrix[0].length + 1];
        resultVector = new Boolean[result.length];


        int errorCount, era = 0, fNetwork;
        float delta, net;

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
            resultWriter.write("      Number of error: " + errorCount + "\n");
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
    public void showFit() throws IOException {
        resultWriter.flush();
    }

    private float net(Boolean[] variables) {
        float result = weights[0];
        for (int i = 1; i < weights.length; ++i) {
            result += (variables[i - 1] ? 1 : 0) * weights[i];
        }
        return result;
    }


    private int fNet(float net) {
        if (!isLogistic) {
            return net < 0 ? 0 : 1;
        } else {
            return (int)Math.round(0.5 + 0.5 * Math.tanh(net));
        }
    }

    private float getCorrectionWeight(Boolean variable, float d, float net) {
        if (!isLogistic) {
            return trainingNorm * d * (variable ? 1 : 0);
        } else {
            return trainingNorm * d * (variable ? 1 : 0) * (float)(0.5 - 0.5 * Math.tanh(net));
        }
    }
}
