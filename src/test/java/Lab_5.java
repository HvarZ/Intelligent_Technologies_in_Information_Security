import com.RainCarnation.NeuralNetworkHopfield;
import com.RainCarnation.service.BinaryImage;

public class Lab_5 {
    public static void main(String[] args) throws Exception {
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

        NeuralNetworkHopfield network = new NeuralNetworkHopfield();
        network.fit(new BinaryImage[]{image_3, image_5, image_7});

        BinaryImage resultImage_3 = network.getResult(image_3);
        BinaryImage resultImage_5 = network.getResult(image_5);
        BinaryImage resultImage_7 = network.getResult(image_7);

        resultImage_3.print();
        resultImage_5.print();
        resultImage_7.print();

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

        corruptResultImage_3.print();
        corruptResultImage_5.print();
        corruptResultImage_7.print();
    }
}
