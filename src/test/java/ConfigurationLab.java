import com.RainCarnation.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileOutputStream;

@Configuration
@PropertySource("classpath:Labs.properties")
public class ConfigurationLab {
    @Value("${numberVariables}")
    int numberVariables;
    @Value("${filenameResult}")
    private String filename;

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
        out = new FileOutputStream(filename, true);
        NeuralNetworkBoolean network = new NeuralNetworkBooleanThreshold(trainingNorm, out);
        Boolean[][] matrixFull = NeuralNetworkBoolean.getStandardMatrix(numberVariables);
        network.fit(matrixFull, NeuralNetworkBoolean.convertStringVectorToArray(resultFull));

        return network;
    }

    @Bean
    public NeuralNetworkBoolean thresholdPart() throws Exception {
        out = new FileOutputStream(filename, true);
        NeuralNetworkBoolean network = new NeuralNetworkBooleanThreshold(trainingNorm, out);
        network.fit(NeuralNetworkBoolean.convertStringVectorsToMatrix(matrixPart), NeuralNetworkBoolean.convertStringVectorToArray(resultPart));

        return network;
    }

    @Bean
    public NeuralNetworkBoolean logisticFull() throws Exception {
        out = new FileOutputStream(filename, true);
        NeuralNetworkBoolean network = new NeuralNetworkBooleanLogistic(trainingNorm, out);
        Boolean[][] matrixFull = NeuralNetworkBoolean.getStandardMatrix(numberVariables);
        network.fit(matrixFull, NeuralNetworkBoolean.convertStringVectorToArray(resultFull));

        return network;
    }


    @Bean
    public NeuralNetworkBoolean logisticPart() throws Exception {
        out = new FileOutputStream(filename, true);
        NeuralNetworkBoolean network = new NeuralNetworkBooleanLogistic(trainingNorm, out);
        network.fit(NeuralNetworkBoolean.convertStringVectorsToMatrix(matrixPart), NeuralNetworkBoolean.convertStringVectorToArray(resultPart));

        return network;
    }
}
