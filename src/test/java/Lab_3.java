import com.RainCarnation.NeuralNetworkBoolean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileWriter;

public class Lab_3 {
    public static void main(String[] args) throws Exception {
        new FileWriter("results/lab_3.txt", false).close();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationLab.class);

        NeuralNetworkBoolean network = context.getBean("extrapolationThreshold", NeuralNetworkBoolean.class);
        network.showFit("============= RBF Threshold =============\n");
        network.showFitGraphics();


        network = context.getBean("extrapolationThreshold", NeuralNetworkBoolean.class);
        network.showFit("============= RBF Logistic =============\n");
        network.showFitGraphics();


        context.close();

    }
}
