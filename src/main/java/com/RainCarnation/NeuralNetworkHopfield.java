package com.RainCarnation;

import com.RainCarnation.service.BinaryImage;

public class NeuralNetworkHopfield extends NeuralNetwork<BinaryImage[], Integer, Integer[], Integer[]> {
    private int[][] weights;
    @Override
    public void fit(BinaryImage[] matrix, Integer... result) throws Exception {

        if (matrix.length <= 0) {
            throw new NeuralException("Input data is clear");
        }

        int[][] vectorizedImages = new int[matrix.length][];

        for (int i = 0; i < matrix.length; ++i) {
            vectorizedImages[i] = BinaryImage.vectorize(matrix[i].getImage());
        }

        weights = new int[vectorizedImages[0].length][];

        for (int i = 0; i < matrix.length; ++i) {
            weights = sum(weights, multiple(vectorizedImages[i], vectorizedImages[i]));
        }
    }

    @Override
    public Integer[] getResult(Integer[] input) throws NeuralException {
        return null;
    }

    @Override
    public void showFitGraphics() throws Exception {

    }

    public static int[][] multiple(int[] a, int[] b) {
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

    public static int[][] sum(int[][] a, int[][] b) {
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


}
