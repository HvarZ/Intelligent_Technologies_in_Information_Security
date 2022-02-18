package com.RainCarnation;

public class Main {

    public static void main(String[] args) {
	    boolean[][] matrix = new boolean[16][4];
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
        System.out.print(matrix[0][0]);
    }
}
