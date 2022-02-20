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

        int errorCount;
        float delta, net;
        int era = 0;

        do {
            era++;
            errorCount = 0;
            System.out.print("Era #" + era);
            for (int i = 0; i < matrix.length; ++i) {
                net = net(matrix[i]);
                delta = (result[i] ? 1 : 0) - fNet(net);
                if (delta != 0) {
                    errorCount++;
                    weights[0] += getCorrectionWeight(true, delta);
                    for (int j = 1; j < weights.length; j++) {
                        weights[j] += getCorrectionWeight(matrix[i][j - 1], delta);
                    }
                }
            }
            System.out.println("       Number error is " + errorCount);
        } while (errorCount != 0);
    }

    public void fitLogistic(Boolean[][] matrix, Boolean[] result) throws NeuralException {
        if (matrix.length <= 0 || matrix[0].length <= 0) {
            throw new NeuralException("Input data is clear");
        }

        if (matrix.length != result.length) {
            throw new NeuralException("Invalid input data");
        }

        weights = new float[matrix[0].length + 1];

        int errorCount;
        float delta, net;
        int era = 0;

        do {
            era++;
            errorCount = 0;
            System.out.print("Era #" + era);
            System.out.print("       Result vector: ");
            printVector();
            for (int i = 0; i < matrix.length; ++i) {
                net = net(matrix[i]);
                delta = (result[i] ? 1 : 0) - fNetLogistic(net);
                if (delta != 0) {
                    errorCount++;
                    weights[0] += getCorrectionWeightLogistic(true, delta, net);
                    for (int j = 1; j < weights.length; j++) {
                        weights[j] += getCorrectionWeightLogistic(matrix[i][j - 1], delta, net);
                    }
                }
            }
            System.out.println("       Number error is " + errorCount);
        } while (errorCount != 0);
    }

    @Override
    public Boolean getResult(Boolean[] input) {
        return fNetLogistic(net(input)) == 1;
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

    private float net(Boolean[] variables) {
        float result = weights[0];
        for (int i = 1; i < weights.length; ++i) {
            result += (variables[i - 1] ? 1 : 0) * weights[i];
        }
        return result;
    }

    private void printVector() {
        Boolean[][] matrix = new Boolean[16][4];
        int levelCounter = 0;
        int levelController = 8;
        boolean setter = false;

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 16; ++j) {
                if (levelController == levelCounter) {
                    setter ^= true;
                    levelCounter = 0;
                }
                matrix[j][i] = setter;
                levelCounter++;
            }
            levelController >>= 1;
            levelCounter = 0;
            setter = false;
        }

        for (Boolean[] string : matrix) {
            System.out.print(getResult(string) ? 1 : 0);
        }
    }


    private int fNet(float net) {
        return net < 0 ? 0 : 1;
    }

    private int fNetLogistic(float net) {
        return (int)Math.round(0.5 + 0.5 * Math.tanh(net));
    }

    private float getCorrectionWeight(Boolean variable, float d) {
        return trainingNorm * d * (variable ? 1 : 0);
    }

    private float getCorrectionWeightLogistic(Boolean variable, float d, float net) {
        return trainingNorm * d * (variable ? 1 : 0) * (float)(0.5 - 0.5 * Math.tanh(net));
    }
}
