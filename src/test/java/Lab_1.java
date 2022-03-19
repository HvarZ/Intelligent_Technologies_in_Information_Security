import com.RainCarnation.NeuralNetworkBoolean;
import com.RainCarnation.NeuralNetworkBooleanLogistic;
import com.RainCarnation.NeuralNetworkBooleanThreshold;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Lab_1 {
    public static void fillStandardMatrix(Boolean[][] matrix) {
        int levelCounter = 0;
        int levelController = (int)Math.pow(2, matrix[0].length - 1);
        boolean setter = false;

        for (int i = 0; i < matrix[0].length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
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
    }

    public static void testingNetwork(NeuralNetworkBoolean network, Boolean[][] matrix, Boolean[] result, String TestName) throws Exception {
        network.fit(matrix, result);
        FileWriter writer = new FileWriter("results/lab_1.txt", true);
        writer.write(TestName + "\n");
        writer.write("Result vector: ");
        Boolean[][] fullMatrix = new Boolean[16][4];
        fillStandardMatrix(fullMatrix);
        for (Boolean[] string : fullMatrix) {
            writer.write(network.getResult(string) ? "1" : "0");
        }
        writer.write('\n');
        writer.flush();
        network.showFit();
        network.showFitGraphics();
    }

    public static void main(String[] args) {
	    Boolean[][] matrix = new Boolean[16][4];
        Boolean[] result = {false, true, false, true,
                            false, true, true, true,
                            false, true, true, true,
                            false, true, true, true,};

        Boolean[][] matrixPart = {{false, false, false, true},
                                  {false, false, true, false},
                                  {false, true, true, false},
                                  {true, false, true, false},
                                  {true, true, false, false}};

        Boolean[] resultPart = {true, false, true, true, false};


        fillStandardMatrix(matrix);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("results/lab_1.txt", true);
            new FileWriter("results/lab_1.txt", false).close();


            NeuralNetworkBoolean network = new NeuralNetworkBooleanThreshold(0.3f, out);
            testingNetwork(network, matrix, result, "################   Threshold activation function   ################");


            NeuralNetworkBoolean network_2 = new NeuralNetworkBooleanThreshold(0.3f, out);
            testingNetwork(network_2, matrixPart, resultPart, "\n\n\n################   Threshold activation function (part data)   ################");


            NeuralNetworkBoolean network_3 = new NeuralNetworkBooleanLogistic(0.3f, out);
            testingNetwork(network_3, matrix, result, "\n\n\n################   Logistic activation function   ################");


            NeuralNetworkBoolean network_4 = new NeuralNetworkBooleanLogistic(0.3f, out);
            testingNetwork(network_4, matrixPart, resultPart, "\n\n\n################   Logistic activation function (part data)   ################");
        } catch (Exception e) {
            System.err.println(e.getMessage());

        } finally {
            try {
                out.close();
            } catch (IOException | NullPointerException e) {
                System.err.println("Invalid closing");
            }
        }
    }
}
