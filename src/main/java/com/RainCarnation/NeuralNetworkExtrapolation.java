package com.RainCarnation;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.SortedMap;


public class NeuralNetworkExtrapolation extends NeuralNetwork<Float, Float> {
    private float trainingNorm;
    private float lengthWindow;
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

        weights = new float[matrix.length + 1];

    }

    @Override
    public Float getResult(Float input) throws NeuralException {
        return null;
    }

    @Override
    public void showFitGraphics() throws Exception {

    }
}
