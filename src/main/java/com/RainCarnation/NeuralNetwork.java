package com.RainCarnation;

import java.io.IOException;
import java.io.OutputStream;

// T - type for input data (test or fit data)
// R - type for result data
public abstract class NeuralNetwork<T, R> {
    protected float[] weights;

    public abstract void fit(T[][] matrix, R[] result) throws Exception;
    public abstract R getResult(T[] input) throws NeuralException;
    public abstract void showFit() throws IOException;
    public abstract void showFitGraphics() throws Exception;
    public abstract String fitResultToString();
    public abstract void saveFitResult();
    public abstract void saveFitResult(OutputStream out) throws IOException;
}