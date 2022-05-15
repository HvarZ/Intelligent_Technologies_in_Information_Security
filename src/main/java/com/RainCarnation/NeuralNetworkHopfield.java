package com.RainCarnation;

import com.RainCarnation.service.BinaryImage;

public class NeuralNetworkHopfield extends NeuralNetwork<BinaryImage[], Integer, BinaryImage, BinaryImage> {
    private int[][] weights;
    private int width;

    @Override
    public void fit(BinaryImage[] images, Integer... result) throws Exception {
        if (images.length <= 0) {
            throw new NeuralException("Input data is clear");
        }
        width = images[0].getImage()[0].length;

        int[][] vectorizedImages = new int[images.length][];

        for (int i = 0; i < images.length; ++i) {
            vectorizedImages[i] = BinaryImage.vectorize(images[i].getImage());
        }

        weights = new int[vectorizedImages[0].length][vectorizedImages[0].length];

        for (int i = 0; i < images.length; ++i) {
            weights = sum(weights, multiple(vectorizedImages[i], vectorizedImages[i]));
        }
    }

    @Override
    public BinaryImage getResult(BinaryImage image) {
        int changes = 1;

        int[] vectorizedImage = BinaryImage.vectorize(image.getImage());

        while (changes != 0) {
            changes = epoch(vectorizedImage);
        }
        return new BinaryImage(vectorizedImage, width);

    }

    @Override
    public void showFitGraphics() throws Exception {

    }

    private int[][] multiple(int[] a, int[] b) {
        if (a.length != b.length) {
            throw new ArithmeticException("Invalid arguments: matrix (a, b)");
        }

        int[][] matrix = new int[a.length][b.length];

        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; j < a.length; ++j) {
                matrix[i][j] = a[i] * b[j];
            }
        }

        return matrix;
    }

    private int[][] sum(int[][] a, int[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new ArithmeticException("Invalid arguments: matrix (a, b)");
        }

        int[][] sum = new int[a.length][a[0].length];

        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; j < a[0].length; ++j) {
                sum[i][j] = a[i][j] + b[i][j];
            }
        }
        return sum;
    }

    private int fNet(int net, int yOld) {
        if (net > 0) {
            return 1;
        } else if (net < 0) {
            return -1;
        } else {
            return yOld;
        }
    }

    private int net(int[] signals, int index) {
        int sum = 0;
        for (int j = 0; j < signals.length; ++j) {
            if (j != index) {
                sum += (signals[j] * weights[index][j]);
            }
        }
        return sum;
    }

    private int epoch(int[] vectorizedImage) {
        int changes = 0;
        int newValue, oldValue;
        int[] output = new int[vectorizedImage.length];

        for (int k = 0; k < vectorizedImage.length; ++k) {
            oldValue = vectorizedImage[k];
            newValue = fNet(net(vectorizedImage, k), oldValue);
            output[k] = newValue;
            if (oldValue != newValue) {
                changes++;
            }
        }
        System.arraycopy(output, 0, vectorizedImage, 0, vectorizedImage.length);

        return changes;
    }
}
