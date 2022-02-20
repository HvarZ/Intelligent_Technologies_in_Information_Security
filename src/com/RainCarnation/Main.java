package com.RainCarnation;

public class Main {
    public static void main(String[] args) {
	    Boolean[][] matrix = new Boolean[16][4];
        Boolean[] result = {false, true, false, true,
                            false, true, true, true,
                            false, true, true, true,
                            false, true, true, true,};
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

        try {
            NeuralNetworkBoolean network = new NeuralNetworkBoolean(0.3f, 0.f);
            network.fitLogistic(matrix, result);
            for (Boolean[] string : matrix) {
                System.out.println(network.getResult(string));
            }
        } catch (NeuralException e) {
            System.err.println(e.getMessage());
        }
    }
}
