import com.RainCarnation.NeuralNetworkExtrapolation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileWriter;


public class Lab_2 {
    public static void main(String[] args) throws Exception {
        new FileWriter("results/lab_2.txt", false).close();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationLab.class);
        NeuralNetworkExtrapolation network = context.getBean("extrapolation", NeuralNetworkExtrapolation.class);

        network.showExtrapolation(4);
        context.close();
    }
}