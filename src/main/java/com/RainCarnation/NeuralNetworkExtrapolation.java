package com.RainCarnation;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class NeuralNetworkExtrapolation extends NeuralNetwork<Float, Float> {
    private float trainingNorm;
    private int lengthWindow;
    private ArrayList<Float> plotResult;

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

        weights = new float[lengthWindow + 1];

        float net, epsilon, delta;

        do {
            epsilon = 0;
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

            epsilon = (float)Math.sqrt(epsilon);
            System.out.println(epsilon);

        } while (epsilon > 0.01f);
    }

    @Override
    public Float getResult(Float input) throws NeuralException {
        return null;
    }

    @Override
    public void showFitGraphics() throws Exception {

    }

    private float net(Float[] variables, int windowStart) {
        float result = 0;
        for (int i = 1; i < weights.length; ++i) {
            result += (weights[i] * variables[windowStart + i]);
        }
        return result + weights[0];
    }

    private float getCorrectionWeight(float delta, float variable) {
        return trainingNorm * delta * variable;
    }
}
