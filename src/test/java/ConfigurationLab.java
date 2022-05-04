import com.RainCarnation.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.io.FileOutputStream;

@Configuration
@PropertySource("classpath:Labs.properties")
public class ConfigurationLab {
    // FirstLab
    @Value("${numberVariables}")
    int numberVariables;
    @Value("${filenameResult}")
    private String filename_1;

    @Value("${trainingNorm}")
    private Double trainingNorm;

    @Value("${fullResultBoolean}")
    private String resultFull;

    @Value("${partMatrixBoolean}")
    private String matrixPart;

    @Value("${partResultBoolean}")
    private String resultPart;

    private FileOutputStream out;


    @Bean
    public NeuralNetworkBoolean thresholdFull() throws Exception {
        out = new FileOutputStream(filename_1, true);
        NeuralNetworkBoolean network = new NeuralNetworkBooleanThreshold(trainingNorm, out);
        Boolean[][] matrixFull = NeuralNetworkBoolean.getStandardMatrix(numberVariables);
        network.fit(matrixFull, NeuralNetworkBoolean.convertStringVectorToArray(resultFull));

        return network;
    }

    @Bean
    public NeuralNetworkBoolean thresholdPart() throws Exception {
        out = new FileOutputStream(filename_1, true);
        NeuralNetworkBoolean network = new NeuralNetworkBooleanThreshold(trainingNorm, out);
        network.fit(NeuralNetworkBoolean.convertStringVectorsToMatrix(matrixPart), NeuralNetworkBoolean.convertStringVectorToArray(resultPart));

        return network;
    }

    @Bean
    public NeuralNetworkBoolean logisticFull() throws Exception {
        out = new FileOutputStream(filename_1, true);
        NeuralNetworkBoolean network = new NeuralNetworkBooleanLogistic(trainingNorm, out);
        Boolean[][] matrixFull = NeuralNetworkBoolean.getStandardMatrix(numberVariables);
        network.fit(matrixFull, NeuralNetworkBoolean.convertStringVectorToArray(resultFull));

        return network;
    }


    @Bean
    @Scope("prototype")
    public NeuralNetworkBoolean logisticPart() throws Exception {
        out = new FileOutputStream(filename_1, true);
        NeuralNetworkBoolean network = new NeuralNetworkBooleanLogistic(trainingNorm, out);
        network.fit(NeuralNetworkBoolean.convertStringVectorsToMatrix(matrixPart), NeuralNetworkBoolean.convertStringVectorToArray(resultPart));

        return network;
    }

    // Second Lab

    @Value("${a_1}")
    private Double a_1;

    @Value("${a_2}")
    private Double a_2;

    @Value("${b_1}")
    private Double b_1;

    @Value("${b_2}")
    private Double b_2;

    @Value("${numberPoints}")
    private int numberPoints;


    @Value("${filename_2}")
    private String filename_2;

    @Value("${trainingNorm_2}")
    private Double trainingNorm_2;

    @Value("${lengthWindow_2}")
    private int lengthWindow_2;

    @Value("${accuracy_2}")
    private Double accuracy_2;

    @Bean
    @Scope("prototype")
    public NeuralNetworkExtrapolation extrapolation() throws Exception {
        out = new FileOutputStream(filename_2, true);
        Double[][] discreteValues = NeuralNetworkExtrapolation.generateDiscreteFunction(a_1, b_1, numberPoints, Math::tan);
        Double[][] discreteValues_2 = NeuralNetworkExtrapolation.generateDiscreteFunction(a_2, b_2, numberPoints, Math::tan);

        NeuralNetworkExtrapolation network = new NeuralNetworkExtrapolation(trainingNorm_2, lengthWindow_2, accuracy_2, out);
        network.fit(discreteValues[0], discreteValues[1]);
        network.addGraphicToPlot(discreteValues[0], discreteValues[1]);
        network.addGraphicToPlot(discreteValues_2[0], discreteValues_2[1]);

        return network;
    }

}
