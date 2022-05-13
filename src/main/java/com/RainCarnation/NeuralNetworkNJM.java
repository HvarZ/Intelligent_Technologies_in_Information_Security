package com.RainCarnation;

import com.github.sh0nk.matplotlib4j.Plot;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public final class NeuralNetworkNJM extends NeuralNetwork<Double[][], Double, Double[], Double[]> {
    private double trainingNorm;
    private int inputs;
    private int numberHiddenNeurons;
    private int numberOutputNeurons;
    private static final String regex = "^\\d+-\\d+-\\d+$";
    private final List<Double> errors = new ArrayList<>();

    private void construct(double trainingNorm_, String architecture, OutputStream out) throws NeuralException {
        if (trainingNorm_ > 1 || trainingNorm_ < 0) {
            throw new NeuralException("Invalid training coefficient");
        }

        trainingNorm = trainingNorm_;
        parseArchitecture(architecture);
        weights = new double[(inputs + 1) * numberHiddenNeurons + (numberHiddenNeurons + 1) * numberOutputNeurons];
        resultWriter = new BufferedWriter(new OutputStreamWriter(out));

        for (int i = 0; i < weights.length; ++i) {
            weights[i] = 0.1 * random();
        }
    }

    public NeuralNetworkNJM(double trainingNorm_, String architecture, OutputStream out) throws NeuralException {
        construct(trainingNorm_, architecture, out);
    }

    public NeuralNetworkNJM(double trainingNorm_, String architecture) throws NeuralException {
        construct(trainingNorm_, architecture, System.out);
    }

    @Override
    public void fit(Double[][] input, Double... result) throws Exception {
        if (input.length <= 0) {
            throw new NeuralException("Fit: Input data is clear");
        }

        double[] outsHidden = new double[numberHiddenNeurons];
        double net = 0;
        int numberEra = 0;
        double epsilon;
        for (Double[] doubles : input) {
            do {
                epsilon = 0;
                numberEra++;
                // 1 layer
                net = getNet(outsHidden, net, doubles);

                double[] outsOut = new double[numberOutputNeurons];

                // 2 layer
                for (int i = 0; i < numberOutputNeurons; ++i) {
                    for (int j = 0; j < numberHiddenNeurons + 1; ++j) {
                        if (j == 0) {
                            net += weights[(i * (numberHiddenNeurons + 1)) + j + ((inputs + 1) * numberHiddenNeurons)];
                        } else {
                            net += outsHidden[j - 1] * weights[(i * (numberHiddenNeurons + 1)) + j + ((inputs + 1) * numberHiddenNeurons)];
                        }
                    }
                    outsOut[i] = fNet(net);
                    net = 0;
                }

                double[] deltaHidden = new double[numberHiddenNeurons];
                double[] deltaOut = new double[numberOutputNeurons];

                // 2 layer (delta)
                for (int i = 0; i < numberOutputNeurons; ++i) {
                    deltaOut[i] = (result[i] - outsOut[i]) * derivativeFNet(outsOut[i]);
                }

                // 1 layer (delta)
                double sum = 0;
                for (int i = 1; i < numberHiddenNeurons + 1; ++i) {
                    for (int j = 0; j < numberOutputNeurons; ++j) {
                        sum += deltaOut[j] * weights[j * i + ((inputs + 1) * numberHiddenNeurons)];
                    }
                    deltaHidden[i - 1] = outsHidden[i - 1] * sum;
                    sum = 0;
                }

                // reload weights
                for (int i = 0; i < numberHiddenNeurons; ++i) {
                    for (int j = 0; j < inputs + 1; ++j) {
                        weights[(i * numberHiddenNeurons) + j] += trainingNorm * doubles[j] * deltaHidden[i];
                    }
                }

                for (int i = 0; i < numberOutputNeurons; ++i) {
                    for (int j = 0; j < numberHiddenNeurons + 1; ++j) {
                        if (j == 0) {
                            weights[(i * (numberHiddenNeurons + 1)) + j + ((inputs + 1) * numberHiddenNeurons)] += trainingNorm * deltaOut[i];
                        } else {
                            weights[(i * (numberHiddenNeurons + 1)) + j + ((inputs + 1) * numberHiddenNeurons)] += trainingNorm * outsHidden[j - 1] * deltaOut[i];
                        }
                    }
                }

                // average error
                for (int i = 0; i < outsOut.length; ++i) {
                    epsilon += pow(result[i] - outsOut[i], 2);
                }

                epsilon = sqrt(epsilon);
                errors.add(epsilon);
                resultWriter.write("|Era #" + numberEra + "   \t"  + "\t|Weights: (");

                for (int i = 0; i < weights.length - 1; ++i) {
                    resultWriter.write( new DecimalFormat("#0.0000").format(weights[i]).replace(',', '.') + ", ");
                }

                resultWriter.write(new DecimalFormat("#0.0000").format(weights[weights.length - 1]).replace(',', '.') + ")" + "\t|");
                resultWriter.write("RMS error: " + new DecimalFormat("#0.00000").format(epsilon).replace(',', '.') + "\t|\n");
            } while (epsilon > 0.001);
        }
    }

    private double getNet(double[] outsHidden, double net, Double[] doubles) {
        for (int i = 1; i < numberHiddenNeurons + 1; ++i) {
            for (int j = 0; j < inputs; ++j) {
                net += doubles[j * i] * weights[j * i];
            }
            outsHidden[i - 1] = fNet(net);
            net = 0;
        }
        return net;
    }

    @Override
    public Double[] getResult(Double[] input) {
        double net = 0;
        double[] outsHidden = new double[numberHiddenNeurons];
        net = getNet(outsHidden, net, input);

        Double[] outsOut = new Double[numberOutputNeurons];

        for (int i = 0; i < numberOutputNeurons; ++i) {
            for (int j = 0; j < numberHiddenNeurons + 1; ++j) {
                if (j == 0) {
                    net += weights[(i * (numberHiddenNeurons + 1)) + j + ((inputs + 1) * numberHiddenNeurons)];
                } else {
                    net += outsHidden[j - 1] * weights[(i * (numberHiddenNeurons + 1)) + j + ((inputs + 1) * numberHiddenNeurons)];
                }
            }
            outsOut[i] = fNet(net);
            net = 0;
        }

        return outsOut;
    }

    @Override
    public void showFitGraphics() throws Exception {
        Plot plot = Plot.create();
        plot.plot().add(errors);
        plot.ylabel("Root-mean-square error");
        plot.show();
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

    private double fNet(double net) {
        return (1 - exp(-net)) / (1 + exp(-net));
    }

    private double derivativeFNet(double fNet) {
        return 0.5 * (1 - pow(fNet, 2));
    }
}
