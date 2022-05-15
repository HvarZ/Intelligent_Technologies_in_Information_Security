package com.RainCarnation.service;

public final class BinaryImage {
    int[][] image;

    public BinaryImage(int[][] matrix) {
        this.image = matrix;
    }

    public BinaryImage(int[] vectorizedImage, int width) {
        image = new int[vectorizedImage.length / width][width];

        for (int i = 0; i < vectorizedImage.length / width; ++i) {
            System.arraycopy(vectorizedImage, i * width, image[i], 0, width);
        }
    }

    public int[][] getImage() {
        return image;
    }

    public static int[] vectorize(int[][] matrix) {
        int[] vectorizeImage = new int[matrix.length * matrix[0].length];

        for (int i = 0; i < matrix.length; ++i) {
            System.arraycopy(matrix[i], 0, vectorizeImage, matrix[0].length * i, matrix[0].length);
        }

        return vectorizeImage;
    }

    public void print() {
        for (int[] line : image) {
            for (int pixel : line) {
                if (pixel == -1) {
                    System.out.print(0);
                } else {
                    System.out.print(pixel);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
