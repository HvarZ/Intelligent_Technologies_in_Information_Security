import com.RainCarnation.NeuralNetworkBoolean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileWriter;

public class Lab_1 {
    private static void testing(AnnotationConfigApplicationContext context, String beanId, String testName) throws Exception {
        NeuralNetworkBoolean network = context.getBean(beanId, NeuralNetworkBoolean.class);
        network.showFit(testName + "\n\n\n");
        network.showFitGraphics();
    }

    public static void main(String[] args) throws Exception {
        new FileWriter("results/lab_1.txt", false).close();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationLab.class);

        testing(context, "thresholdFull", "################   Threshold activation function   ################\n\n\n");
        testing(context, "thresholdPart", "################   Threshold activation function (part data)   ################\n\n\n");
        testing(context, "logisticFull", "################   Logistic activation function   ################\n\n\n");
        testing(context, "logisticPart", "################   Logistic activation function (part data)   ################\n\n\n");

        context.close();
    }
}
