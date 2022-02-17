package com.RainCarnation;

public class NeuralNetworkBoolean extends NeuralNetwork<Boolean, Boolean>  {
    public NeuralNetworkBoolean() {
        weights = null;
        variables = null;
    }

    @Override
    public void fit(Boolean[][] matrix, Boolean[] result) throws NeuralException {
        if (matrix.length <= 0 || matrix[0].length <= 0) {
            throw new NeuralException("Input data is clear");
        }
        weights = new float[matrix[0].length];
        variables = new Boolean[matrix[0].length];
    }

    @Override
    public Boolean getResult(Boolean[] input) {
        return true;
    }
}
