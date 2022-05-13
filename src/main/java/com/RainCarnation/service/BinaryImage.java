package com.RainCarnation.service;

public final class BinaryImage {
    int[][] image;

    public BinaryImage(int[][] matrix) {
        this.image = matrix;
    }

    public int[][] getImage() {
        return image;
    }

    public static int[] vectorize(int[][] matrix) {
        int[] vectorizeImage = new int[matrix.length * matrix[0].length];

        for (int i = 0; i < matrix.length; ++i) {
            System.arraycopy(matrix[i], 0, vectorizeImage, matrix.length * i, matrix[0].length);
        }

        return vectorizeImage;
    }

}
