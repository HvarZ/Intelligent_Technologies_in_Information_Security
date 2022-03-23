package com.RainCarnation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

// T - type for input data (test or fit data)
// R - type for result data
public abstract class NeuralNetwork<T, R> {
    protected double[] weights;
    protected BufferedWriter resultWriter;
    protected String fitInfo;

    public abstract void fit(T[] matrix, R[] result) throws Exception;

    public abstract R getResult(T input) throws NeuralException;

    public abstract void showFitGraphics() throws Exception;

    public void showFit() throws IOException {
        resultWriter.flush();
    }

    public String fitResultToString() {
        return resultWriter.toString();
    }

    public void saveFitResult() {
        fitInfo = fitResultToString();
    }

    public void saveFitResult(OutputStream out) throws IOException {
        fitInfo = fitResultToString();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        writer.write(fitInfo);
    }
}