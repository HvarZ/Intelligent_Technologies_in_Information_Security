package com.RainCarnation;

import java.io.OutputStream;

public class NeuralNetworkBooleanRBF extends NeuralNetworkBoolean {
    public NeuralNetworkBooleanRBF(double trainingNorm_, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        construct(trainingNorm_, out);
    }

    public NeuralNetworkBooleanRBF(double trainingNorm) throws NeuralException {
        this(trainingNorm, System.out);
    }

    @Override
    public void fit(Boolean[][] matrix, Boolean[] result) throws Exception {

    }

    @Override
    public Boolean getResult(Boolean[] input) {

        return true;
    }

    @Override
    protected int fNet(double net) {
        return 0;
    }

    @Override
    protected double getCorrectionWeight(boolean variable, double d, double net) {
        return 0;
    }
}
