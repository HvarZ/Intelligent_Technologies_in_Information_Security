package com.RainCarnation;

public class NeuralNetworkBoolean extends NeuralNetwork<Boolean, Boolean>  {
    private float trainingNorm;
    private float probabilityError;

    public NeuralNetworkBoolean(float trainingNorm_, float probabilityError_) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        if (probabilityError_ >= 1 || trainingNorm_ <= 0) {
            throw new NeuralException("Invalid probability");
        }
        trainingNorm = trainingNorm_;
        probabilityError = probabilityError_;
        weights = null;
    }

    @Override
    public void fit(Boolean[][] matrix, Boolean[] result) throws NeuralException {
        if (matrix.length <= 0 || matrix[0].length <= 0) {
            throw new NeuralException("Input data is clear");
        }
        if (matrix.length != result.length) {
            throw  new NeuralException("Invalid input data");
        }

        weights = new float[matrix[0].length + 1];
    }

    @Override
    public Boolean getResult(Boolean[] input) {
        return true;
    }

    private int net(Boolean[] variables) {
        int result = 0;
        for (int i = 0; i < variables.length; ++i) {
            result += (variables[i] ? 1 : 0) * weights[i];
        }
        return result;
    }

    private int fNet(int net) {
        return net <= 0 ? 0 : 1;
    }

    private int delta(int t, int y) {
        return t - y;
    }
}
