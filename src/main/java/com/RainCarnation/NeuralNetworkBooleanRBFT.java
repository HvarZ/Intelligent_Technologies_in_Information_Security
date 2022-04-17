package com.RainCarnation;

import java.io.OutputStream;

public class NeuralNetworkBooleanRBFT extends NeuralNetworkBooleanRBF {
    public NeuralNetworkBooleanRBFT(double trainingNorm_, Boolean[] result, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        resultBooleanFunction = result;
        construct(trainingNorm_, out);
    }

    public NeuralNetworkBooleanRBFT(double trainingNorm, Boolean[] result) throws NeuralException {
        this(trainingNorm, result, System.out);
    }

    @Override
    protected int fNet(double net) {
        return net >= 0 ? 1 : 0;
    }
}
