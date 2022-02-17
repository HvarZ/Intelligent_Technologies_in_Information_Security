package com.RainCarnation;

public class NeuralNetworkBoolean extends NeuralNetwork<Boolean, Boolean>  {
    public void fit(Boolean[][] matrix, Boolean[] result) {}
    public Boolean getResult(Boolean[] input) {
        return true;
    }
}
