package mnist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  @author Marcin Mikosik (https://github.com/mikosik)
 */
public class MnistReader
{
    private static final String DIR                  =
        "C:/Users/karln/.spyder-py3/MNIST_data/";
    private static final String TRAINING_IMAGES_FILE =
        DIR + "train-images-idx3-ubyte";
    private static final String TRAINING_LABELS_FILE =
        DIR + "train-labels-idx1-ubyte";
    private static final String TEST_IMAGES_FILE     =
        DIR + "t10k-images-idx3-ubyte";
    private static final String TEST_LABELS_FILE     =
        DIR + "t10k-labels-idx1-ubyte";


    public static List<MnistNumber> readTrainingSet() throws IOException
    {
        return readSet(TRAINING_IMAGES_FILE, TRAINING_LABELS_FILE);
    }


    public static List<MnistNumber> readTestSet() throws IOException
    {
        return readSet(TEST_IMAGES_FILE, TEST_LABELS_FILE);
    }


    private static List<MnistNumber> readSet(
        String trainingImagesFile,
        String trainingLabelsFile)
        throws IOException
    {
        ArrayList<MnistNumber> result = new ArrayList<MnistNumber>();
        try (
            MnistImagesReader imagesReader = MnistImagesReader.open(trainingImagesFile);
            MnistLabelsReader labelsReader = MnistLabelsReader.open(trainingLabelsFile))
        {
            if (imagesReader.imageCount != labelsReader.labelsCount)
            {
                throw new RuntimeException(
                    "images count (" + imagesReader.imageCount
                        + ") differ from labels count ("
                        + labelsReader.labelsCount + ").");
            }
            while (imagesReader.hasNext() && labelsReader.hasNext())
            {
                result.add(new MnistNumber(imagesReader.next(), labelsReader.next()));
            }
        }
        return result;
    }
}
