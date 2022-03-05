package com.RainCarnation;

import java.io.OutputStream;

public class NeuralNetworkBooleanLogistic extends NeuralNetworkBoolean {
    public NeuralNetworkBooleanLogistic(float trainingNorm_, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        construct(trainingNorm_, out);
    }

    public NeuralNetworkBooleanLogistic(float trainingNorm) throws NeuralException {
        this(trainingNorm, System.out);
    }

    @Override
    protected int fNet(float net) {
        return (int) Math.round(0.5 + 0.5 * Math.tanh(net));
    }

    @Override
    protected float getCorrectionWeight(Boolean variable, float d, float net) {
        return trainingNorm * d * (variable ? 1 : 0) * (float) (Math.pow(1 / Math.cosh(net), 2) / 2);
    }
}
