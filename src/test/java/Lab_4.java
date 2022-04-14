import com.RainCarnation.NeuralNetworkNJM;
import java.io.FileOutputStream;

public class Lab_4 {
    public static void main(String[] args) throws Exception {
        FileOutputStream out = new FileOutputStream("results/lab_4.txt");

        NeuralNetworkNJM network = new NeuralNetworkNJM(1, "2-1-2", out);
        Double[][] testData = new Double[][]{{1d, 1d, 2d}};

        network.fit(testData, new Double[]{0.2, 0.2});
        Double[] result = network.getResult(new Double[]{0.1d, 0.1d, 0.2d});

        System.out.println(result[0] + " " + result[1]);
        network.showFitGraphics();
        network.showFit();
    }
}
