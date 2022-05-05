import com.RainCarnation.NeuralNetwork;
import com.RainCarnation.NeuralNetworkNJM;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileWriter;

public class Lab_4 {
    public static void main(String[] args) throws Exception {
        new FileWriter("results/lab_4.txt", false).close();
        AnnotationConfigApplicationContext context = new
                AnnotationConfigApplicationContext(ConfigurationLab.class);
        NeuralNetworkNJM network = context.getBean("njm", NeuralNetworkNJM.class);
        Double[] result = network.getResult(new Double[]{0.1d, 0.1d, 0.2d});
        System.out.println(result[0] + " " + result[1]);
        network.showFitGraphics();
        network.showFit("################   NJM   ################" + "\n");
        context.close();
    }
}
