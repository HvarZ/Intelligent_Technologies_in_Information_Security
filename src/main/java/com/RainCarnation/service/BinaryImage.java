package com.RainCarnation.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

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

    public void print(OutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        for (int[] line : image) {
            for (int pixel : line) {
                if (pixel == -1) {
                    writer.write("0");
                } else {
                    writer.write(Integer.toString(pixel));
                }
            }
            writer.write('\n');
        }
        writer.write('\n');
        writer.flush();
    }

}
