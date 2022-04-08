package com.RainCarnation;

import java.io.OutputStream;

public class NeuralNetworkNJM extends NeuralNetwork<Double, Double> {
    double trainingNorm;
    private int inputs;
    private int numberHiddenNeurons;
    private int numberOutputNeurons;
    private static final String regex = "^[0-9][0-9]*-[0-9][0-9]*-[0-9][0-9]*$";

    private void construct(double trainingNorm_, String architecture, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }

        trainingNorm = trainingNorm_;
        parseArchitecture(architecture);
        weights = new double[(inputs + 1) * numberHiddenNeurons + (numberHiddenNeurons + 1) * numberOutputNeurons];
    }

    public NeuralNetworkNJM(double trainingNorm_, String architecture, OutputStream out) throws NeuralException {
        construct(trainingNorm_, architecture, out);
    }

    public NeuralNetworkNJM(double trainingNorm_, String architecture) throws NeuralException {
        construct(trainingNorm_, architecture, System.out);
    }

    @Override
    public void fit(Double[] matrix, Double[] result) throws Exception {

    }

    @Override
    public Double getResult(Double input) throws NeuralException {
        return null;
    }

    @Override
    public void showFitGraphics() throws Exception {

    }

    private void parseArchitecture(String architecture) throws NeuralException {
        if (!architecture.matches(regex)) {
            throw new NeuralException("Invalid architecture");
        }
        String[] architectureValue = architecture.split("-");
        inputs = Integer.parseInt(architectureValue[0]);
        numberHiddenNeurons = Integer.parseInt(architectureValue[1]);
        numberOutputNeurons = Integer.parseInt(architectureValue[2]);
    }
}
