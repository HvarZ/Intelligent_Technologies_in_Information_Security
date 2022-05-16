import com.RainCarnation.NeuralNetworkHopfield;
import com.RainCarnation.service.BinaryImage;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.Writer;

public class Lab_5 {
    public static void main(String[] args) throws Exception {
        String pathResult = "results/lab_5.txt";
        new FileWriter(pathResult, false).close();
        OutputStream out = new FileOutputStream(pathResult, true);
        Writer writer = new FileWriter(pathResult, true);

        BinaryImage image_3 = new BinaryImage(new int[][]{
                {+1, +1, +1, +1, +1},
                {-1, -1, -1, -1, +1},
                {-1, -1, -1, -1, +1},
                {-1, +1, +1, +1, -1},
                {-1, -1, -1, -1, +1},
                {-1, -1, -1, -1, +1},
                {+1, +1, +1, +1, +1}
        });

        BinaryImage image_5 = new BinaryImage(new int[][]{
                {+1, +1, +1, +1, +1},
                {+1, -1, -1, -1, -1},
                {+1, -1, -1, -1, -1},
                {+1, +1, +1, +1, +1},
                {-1, -1, -1, -1, +1},
                {-1, -1, -1, -1, +1},
                {+1, +1, +1, +1, +1}
        });

        BinaryImage image_7 = new BinaryImage(new int[][]{
                {+1, +1, +1, +1, +1},
                {-1, -1, -1, +1, -1},
                {-1, -1, -1, +1, -1},
                {-1, -1, +1, -1, -1},
                {-1, -1, +1, -1, -1},
                {-1, +1, -1, -1, -1},
                {-1, +1, -1, -1, -1}
        });

        NeuralNetworkHopfield network = new NeuralNetworkHopfield(out);
        network.fit(new BinaryImage[]{image_3, image_5, image_7});
        network.showFit("\n\n\n");

        BinaryImage resultImage_3 = network.getResult(image_3);
        BinaryImage resultImage_5 = network.getResult(image_5);
        BinaryImage resultImage_7 = network.getResult(image_7);

        writer.write("===================   Undistorted Data   ===================\n");
        writer.flush();

        resultImage_3.print(out);
        resultImage_5.print(out);
        resultImage_7.print(out);


        writer.write("===================   Undistorted Data   ===================\n");
        writer.flush();

        BinaryImage corrupt_image_3 = new BinaryImage(new int[][]{
                {+1, +1, +1, +1, +1},
                {-1, -1, -1, -1, +1},
                {-1, -1, -1, -1, +1},
                {-1, +1, -1, +1, -1},
                {-1, -1, -1, -1, +1},
                {+1, -1, +1, -1, +1},
                {+1, +1, +1, +1, +1}
        });

        BinaryImage corrupt_image_5 = new BinaryImage(new int[][]{
                {+1, +1, +1, +1, +1},
                {+1, -1, -1, +1, -1},
                {+1, -1, -1, -1, -1},
                {+1, +1, -1, +1, +1},
                {-1, +1, -1, -1, +1},
                {-1, -1, -1, -1, +1},
                {+1, +1, +1, -1, +1}
        });

        BinaryImage corrupt_image_7 = new BinaryImage(new int[][]{
                {+1, +1, +1, +1, +1},
                {-1, -1, -1, +1, -1},
                {-1, -1, -1, +1, -1},
                {-1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1},
                {-1, +1, -1, -1, -1},
                {-1, +1, -1, -1, +1}
        });

        BinaryImage corruptResultImage_3 = network.getResult(corrupt_image_3);
        BinaryImage corruptResultImage_5 = network.getResult(corrupt_image_5);
        BinaryImage corruptResultImage_7 = network.getResult(corrupt_image_7);

        corruptResultImage_3.print(out);
        corruptResultImage_5.print(out);
        corruptResultImage_7.print(out);
    }
}
