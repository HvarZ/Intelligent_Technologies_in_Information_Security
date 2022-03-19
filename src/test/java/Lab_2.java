import com.RainCarnation.NeuralNetworkExtrapolation;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Lab_2 {
    private static Float[] generateNormalDistribution(float a, float b, int number) {
        Float[] result = new Float[number];
        float turn = (b - a) / (number + 1);

        for (int i = 1; i < number + 1; ++i) {
            result[i - 1] = a + i * turn;
        }
        return result;
    }
    private static Float[] generateDiscreteValues(Float[] discreteNumbers) {
        Float[] result = new Float[discreteNumbers.length];

        for (int i = 0; i < result.length; ++i) {
            // result[i] = (float)Math.exp(-0.1 * (discreteNumbers[i] * discreteNumbers[i]));
            // result[i] = (float)(0.5 * Math.cos(0.5 * discreteNumbers[i]) - 0.5);
            result[i] = (float)(Math.tan(discreteNumbers[i]));
        }

        return result;
    }


    public static void main(String[] args) {
        Float[] discreteNumbers = generateNormalDistribution(2f, 3f, 20);
        Float[] discreteValues = generateDiscreteValues(discreteNumbers);

        Float[] discreteNumber_2 = generateNormalDistribution(3f, 4f, 20);
        Float[] discreteValues_2 = generateDiscreteValues(discreteNumber_2);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("results/lab_2.txt", true);
            new FileWriter("results/lab_2.txt", false).close();
            NeuralNetworkExtrapolation network = new NeuralNetworkExtrapolation(0.1f, 8, 0.0008f, out);
            network.fit(discreteNumbers, discreteValues);
            network.showFitGraphics();
            network.addGraphicToPlot(discreteNumbers, discreteValues);
            network.addGraphicToPlot(discreteNumber_2, discreteValues_2);
            network.showExtrapolation(4f);
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
