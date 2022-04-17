import com.RainCarnation.NeuralNetworkBoolean;
import com.RainCarnation.NeuralNetworkBooleanRBFL;
import com.RainCarnation.NeuralNetworkBooleanRBFT;

import java.io.FileOutputStream;
import java.io.FileWriter;

public class Lab_3 {
    public static void main(String[] args) throws Exception {
        Boolean[][] matrixFull = NeuralNetworkBoolean.getStandardMatrix(4);

        Boolean[] resultBooleanFunction = {false, true, false, true,
                false, true, true, true,
                false, true, true, true,
                false, true, true, true};

        Boolean[][] matrix = {{false, false, false, true},
                {false, false, true, false},
                {false, true, true, false},
                {true, false, true, false},
                {true, true, false, false},
                {true, true, true, true}};

        Boolean[] result = {true, false, true, true, false, true};

        FileOutputStream out = new FileOutputStream("results/lab_3.txt", true);
        new FileWriter("results/lab_3.txt", false).close();

        FileWriter writer = new FileWriter("results/lab_3.txt", true);
        writer.write("============= RBF Threshold =============\n");
        writer.flush();

        NeuralNetworkBoolean network = new NeuralNetworkBooleanRBFT(0.3 , resultBooleanFunction, out);
        network.fit(matrix, result);
        network.showFit();
        network.showFitGraphics();

        for (Boolean[] standardVector : matrixFull) {
            System.out.println(network.getResult(standardVector));
        }

        writer.write("\n\n\n");

        writer.write("============= RBF Logistic =============\n");
        writer.flush();
        network = new NeuralNetworkBooleanRBFL(0.3, resultBooleanFunction, out);
        network.fit(matrix, result);
        network.showFit();
        network.showFitGraphics();

        for (Boolean[] standardVector : matrixFull) {
            System.out.println(network.getResult(standardVector));
        }
    }
}
