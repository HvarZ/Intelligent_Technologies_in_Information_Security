package com.RainCarnation;

import java.io.OutputStream;

public class NeuralNetworkBooleanRBFL extends NeuralNetworkBooleanRBF {
    public NeuralNetworkBooleanRBFL(double trainingNorm_, Boolean[] result, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        resultBooleanFunction = result;
        construct(trainingNorm_, out);
    }

    public NeuralNetworkBooleanRBFL(double trainingNorm, Boolean[] result) throws NeuralException {
        this(trainingNorm, result, System.out);
    }

    @Override
    protected int fNet(double net) {
        return (int) Math.round(0.5 + 0.5 * Math.tanh(net));
    }
}
