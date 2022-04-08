import com.RainCarnation.NeuralNetworkNJM;

public class Lab_4 {
    public static void main(String[] args) throws Exception {
        NeuralNetworkNJM network = new NeuralNetworkNJM(1, "2-1-2");

        network.fit(new Double[]{1d, 1d, 2d}, new Double[]{2d, 2d});
    }
}
