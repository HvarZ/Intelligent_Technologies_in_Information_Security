import com.RainCarnation.NeuralException;
import com.RainCarnation.NeuralNetworkExtrapolation;

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
            result[i] = (float)Math.exp(-0.1 * (discreteNumbers[i] * discreteNumbers[i]));
        }

        return result;
    }



    public static void main(String[] args) {
        Float[] discreteNumbers = generateNormalDistribution(-5, 5, 20);
        Float[] discreteValues = generateDiscreteValues(discreteNumbers);

        try {
            NeuralNetworkExtrapolation network = new NeuralNetworkExtrapolation(0.9f, 6);
            network.fit(discreteNumbers, discreteValues);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}
