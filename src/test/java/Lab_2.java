import com.RainCarnation.NeuralNetworkExtrapolation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Lab_2 {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationLab.class);
        NeuralNetworkExtrapolation network = context.getBean("extrapolation", NeuralNetworkExtrapolation.class);

        network.showExtrapolation(4);
        context.close();
    }
}