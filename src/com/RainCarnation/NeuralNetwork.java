package com.RainCarnation;

// T - type for input data (test or fit data)
// R - type for result data
public abstract class NeuralNetwork<T, R> {
    private float[] weights;
    private T[] variables;


    public abstract void fit(T[][] matrix, R[] result);
    public abstract R getResult(T[] input);
}