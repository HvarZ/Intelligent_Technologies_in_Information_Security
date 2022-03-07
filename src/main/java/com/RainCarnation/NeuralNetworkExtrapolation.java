package com.RainCarnation;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.SortedMap;


public class NeuralNetworkExtrapolation extends NeuralNetwork<Float, Float> {
    private float trainingNorm;
    private int lengthWindow;
    private SortedMap<Float, Float> plotResult;

    private void construct(float trainingNorm_, int lengthWindow_, OutputStream out) {
        this.trainingNorm = trainingNorm_;
        this.lengthWindow = lengthWindow_;
        weights = null;

        resultWriter = new BufferedWriter(new OutputStreamWriter(out));
        plotResult = null;
    }

    public NeuralNetworkExtrapolation(float trainingNorm_, int lengthWindow_, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        if (lengthWindow_ <= 0) {
            throw new NeuralException("Invalid length of window");
        }
        construct(trainingNorm_, lengthWindow_, out);
    }

    public NeuralNetworkExtrapolation(float trainingNorm_, int lengthWindow_) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        if (lengthWindow_ <= 0) {
            throw new NeuralException("Invalid length of window");
        }
        construct(trainingNorm_, lengthWindow_, System.out);
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

        weights = new float[lengthWindow];
        //weights[0] = 1;

        float epsilon;

        float net, fNet, delta;
        int era = 0;

        do {
            epsilon = 0;
            era++;
            for (int i = 0; i < matrix.length - weights.length; ++i) {
                net = net(result);
                fNet = fNet(net);
                delta = result[weights.length + i] - fNet;
                epsilon += delta * delta;

                for (int j = 0; j < weights.length; ++j) {
                    weights[j] += getCorrectionWeight(delta, result[i + j]);
                }
            }

            epsilon = (float)Math.sqrt(epsilon);

        } while (epsilon > 0.25f);

        System.out.println(era);
    }

    @Override
    public Float getResult(Float input) throws NeuralException {
        return null;
    }

    @Override
    public void showFitGraphics() throws Exception {

    }

    private float net(Float[] variables) {
        float result = 0;
        for (int i = 0; i < weights.length; ++i) {
            result += weights[i] * variables[variables.length - weights.length + i - 1];
        }
        return result + average(variables);
    }

    private float fNet(float net) {
        return net;
    }

    private static float average(Float[] values) {
        float result = 0;
        for (Float value : values) {
            result += value;
        }

        return result / values.length;
    }

    private float getCorrectionWeight(float delta, float variable) {
        return trainingNorm * delta * variable;
    }
}
