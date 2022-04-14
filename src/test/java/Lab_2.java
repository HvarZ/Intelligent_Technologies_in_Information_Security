import com.RainCarnation.NeuralNetworkExtrapolation;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Lab_2 {
    @FunctionalInterface
    public interface Function {
        double result(double i);
    }

    private static Double[][] generateDiscreteFunction(double a, double b, int number, Function function) {
        Double[][] result = new Double[2][number];
        double turn = (b - a) / (number + 1);

        for (int i = 1; i < number + 1; ++i) {
            result[0][i - 1] = a + i * turn;
            result[1][i - 1] = function.result(a + i * turn);
        }
        return result;
    }


    public static void main(String[] args) {
        Double[][] discreteValues = generateDiscreteFunction(2, 3, 20, Math::tan);
        Double[][] discreteValues_2 = generateDiscreteFunction(3, 4, 20, Math::tan);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("results/lab_2.txt", true);
            new FileWriter("results/lab_2.txt", false).close();
            NeuralNetworkExtrapolation network = new NeuralNetworkExtrapolation(0.1f, 6, 0.001f, out);
            network.fit(discreteValues[0], discreteValues[1]);
            network.addGraphicToPlot(discreteValues[0], discreteValues[1]);
            network.addGraphicToPlot(discreteValues_2[0], discreteValues_2[1]);
            network.showExtrapolation(4);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException | NullPointerException e) {
                System.err.println("Invalid closing");
            }
        }
    }
}