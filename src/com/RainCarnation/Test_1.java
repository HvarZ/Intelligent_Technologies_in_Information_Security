package com.RainCarnation;

public class Test_1 {
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

        System.out.println("################   Threshold activation function   ################");
        try {
            NeuralNetworkBoolean network = new NeuralNetworkBoolean(0.3f);
            network.fit(matrix, result);
            System.out.print("Result vector: ");
            for (Boolean[] string : matrix) {
                System.out.print(network.getResult(string) ? 1 : 0);
            }
            System.out.println();
            network.showFit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n\n\n");
        System.out.println("################   Logistic activation function   ################");

        try {
            NeuralNetworkBoolean network = new NeuralNetworkBoolean(0.3f);
            network.fitLogistic(matrix, result);
            System.out.print("Result vector: ");
            for (Boolean[] string : matrix) {
                System.out.print(network.getResultLogistic(string) ? 1 : 0);
            }
            System.out.println();
            network.showFit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
