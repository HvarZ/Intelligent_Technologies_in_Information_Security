package com.RainCarnation;

import java.io.OutputStream;

public class NeuralNetworkBooleanThreshold extends NeuralNetworkBoolean {
    public NeuralNetworkBooleanThreshold(float trainingNorm_, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        construct(trainingNorm_, out);
    }

    public NeuralNetworkBooleanThreshold(float trainingNorm) throws NeuralException {
        this(trainingNorm, System.out);
    }

    @Override
    protected int fNet(float net) {
        return net < 0 ? 0 : 1;
    }

    @Override
    protected float getCorrectionWeight(boolean variable, float d, float net) {
        return trainingNorm * d * (variable ? 1 : 0);
    }
}
