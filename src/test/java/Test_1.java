import com.RainCarnation.NeuralNetworkBoolean;
import com.RainCarnation.NeuralNetworkBooleanLogistic;
import com.RainCarnation.NeuralNetworkBooleanThreshold;

public class Test_1 {
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

    public static void testingNetwork(NeuralNetworkBoolean network, Boolean[][] matrix, Boolean[] result) throws Exception {
        network.fit(matrix, result);
        System.out.print("Result vector: ");
        Boolean[][] fullMatrix = new Boolean[16][4];
        fillStandardMatrix(fullMatrix);
        for (Boolean[] string : fullMatrix) {
            System.out.print(network.getResult(string) ? 1 : 0);
        }
        System.out.println();
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
                                  {false, false, true, true},
                                  {false, true, false, false},
                                  {false, true, false, true},
                                  {false, true, true, false},
                                  {false, true, true, true},
                                  {true, false, false, false},
                                  {true, false, true, false},
                                  {true, true, false, false},
                                  {true, true, true, false}};

        Boolean[] resultPart = {true, false, true, false,
                                true, true, true, false,
                                true, false, true};

        fillStandardMatrix(matrix);


        System.out.println("################   Threshold activation function   ################");

        try {
            NeuralNetworkBoolean network = new NeuralNetworkBooleanThreshold(0.3f);
            testingNetwork(network, matrix, result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n\n\n");
        System.out.println("################   Threshold activation function (part data)   ################");

        try {
            NeuralNetworkBoolean network = new NeuralNetworkBooleanThreshold(0.3f);
            testingNetwork(network, matrixPart, resultPart);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n\n\n");
        System.out.println("################   Logistic activation function   ################");

        try {
            NeuralNetworkBoolean network = new NeuralNetworkBooleanLogistic(0.3f);
            testingNetwork(network, matrix, result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n\n\n");
        System.out.println("################   Logistic activation function (part data)   ################");

        try {
            NeuralNetworkBoolean network = new NeuralNetworkBooleanLogistic(0.3f);
            testingNetwork(network, matrixPart, resultPart);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
