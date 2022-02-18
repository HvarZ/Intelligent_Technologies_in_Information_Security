package com.RainCarnation;

public class NeuralNetworkBoolean extends NeuralNetwork<Boolean, Boolean>  {
    private float trainingNorm;
    private float probabilityError;

    public NeuralNetworkBoolean(float trainingNorm_, float probabilityError_) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }
        if (probabilityError_ >= 1 || probabilityError_ < 0) {
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

        int net = net(matrix[0]);
        int fNet = fNet(net);
        float delta = fNet - (result[0] ? 1 : 0);
        int errorCount = (int)delta;

        while (errorCount != 0) {
            errorCount = 0;
            for (int i = 0; i < matrix.length; ++i) {
                net = net(matrix[i]);
                fNet = fNet(net);
                delta = (result[i] ? 1 : 0) - fNet;
                if (delta != 0) {
                    errorCount++;
                    weights[0] += getCorrectionWeight(true, delta);
                    for (int j = 1; j < weights.length; j++) {
                        weights[j] += getCorrectionWeight(matrix[i][j - 1], delta);
                    }
                }
            }
        }
    }

    @Override
    public Boolean getResult(Boolean[] input) {
        return fNet(net(input)) == 1;
    }

    public float getTrainingNorm() {
        return trainingNorm;
    }

    public void setTrainingNorm(float trainingNorm) {
        this.trainingNorm = trainingNorm;
    }

    public float getProbabilityError() {
        return probabilityError;
    }

    public void setProbabilityError(float probabilityError) {
        this.probabilityError = probabilityError;
    }

    private int net(Boolean[] variables) {
        int result = (int)weights[0];
        for (int i = 1; i < weights.length; ++i) {
            result += (variables[i - 1] ? 1 : 0) * weights[i];
        }
        return result;
    }

    private int fNet(int net) {
        return net < 0 ? 0 : 1;
    }

    private float getCorrectionWeight(boolean variable, float errorCoefficient) {
        return trainingNorm * errorCoefficient * (variable ? 1 : 0);
    }
}
