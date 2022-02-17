package com.RainCarnation;

// T - type for input data (test or fit data)
// R - type for result data
public abstract class NeuralNetwork<T, R> {
    protected float[] weights;
    protected T[] variables;

    public abstract void fit(T[][] matrix, R[] result) throws NeuralException;
    public abstract R getResult(T[] input) throws NeuralException;
}