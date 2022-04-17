import com.RainCarnation.NeuralNetworkBoolean;
import com.RainCarnation.NeuralNetworkBooleanRBF;

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

        NeuralNetworkBoolean network = new NeuralNetworkBooleanRBF(0.3 , resultBooleanFunction);
        network.fit(matrix, result);

        for (Boolean[] standardVector : matrixFull) {
            System.out.println(network.getResult(standardVector));
        }
    }
}
