package com.RainCarnation;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.List;

import com.github.sh0nk.matplotlib4j.Plot;

public abstract class NeuralNetworkBoolean extends NeuralNetwork<Boolean[], Boolean> {
    protected float trainingNorm;

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

        weights = new float[matrix[0].length + 1];
        resultVector = new Boolean[result.length];
        numberErrors = new ArrayList<>();


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


    protected float net(Boolean[] variables) {
        float result = weights[0];
        for (int i = 1; i < weights.length; ++i) {
            result += (variables[i - 1] ? 1 : 0) * weights[i];
        }
        return result;
    }

    protected void construct(float trainingNorm_, OutputStream out) {
        trainingNorm = trainingNorm_;
        weights = null;

        resultWriter = new BufferedWriter(new OutputStreamWriter(out));
        resultVector = null;
    }

    protected abstract int fNet(float net);

    protected abstract float getCorrectionWeight(Boolean variable, float d, float net);
}
