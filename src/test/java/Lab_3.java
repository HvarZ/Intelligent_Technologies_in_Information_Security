import com.RainCarnation.NeuralNetworkBoolean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileWriter;

public class Lab_3 {
    private static void testing(AnnotationConfigApplicationContext context, String beanId, String report) throws Exception {
        NeuralNetworkBoolean network = context.getBean(beanId, NeuralNetworkBoolean.class);
        network.showFit(report);
        network.showFitGraphics();
    }
    public static void main(String[] args) throws Exception {
        new FileWriter("results/lab_3.txt", false).close();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationLab.class);
        testing(context, "extrapolationThreshold", "============= RBF Threshold =============\n");
        testing(context, "extrapolationLogistic", "============= RBF Logistic =============\n");
        context.close();

    }
}
