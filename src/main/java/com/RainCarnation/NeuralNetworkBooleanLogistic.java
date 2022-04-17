package com.RainCarnation;

import java.io.OutputStream;

public final class NeuralNetworkBooleanLogistic extends NeuralNetworkBoolean {
    public NeuralNetworkBooleanLogistic(double trainingNorm_, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        construct(trainingNorm_, out);
    }

    public NeuralNetworkBooleanLogistic(double trainingNorm) throws NeuralException {
        this(trainingNorm, System.out);
    }

    @Override
    protected int fNet(double net) {
        return (int) Math.round(0.5 + 0.5 * Math.tanh(net));
    }

    @Override
    protected double getCorrectionWeight(boolean variable, double d, double net) {
        return trainingNorm * d * (variable ? 1 : 0) * (Math.pow(1 / Math.cosh(net), 2) / 2);
    }
}
