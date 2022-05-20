package com.RainCarnation;

import com.RainCarnation.service.Measurable;

public class NeuralNetworkKohonen<T extends Measurable, Cluster> extends NeuralNetwork<T[], Cluster[], T, Cluster> {
    @Override
    public void fit(T[] matrix, Cluster[]... result) throws Exception {

    }

    @Override
    public Cluster getResult(T input) throws NeuralException {
        return null;
    }

    @Override
    public void showFitGraphics() throws Exception {

    }
}
