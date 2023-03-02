package mnist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  @author Marcin Mikosik (https://github.com/mikosik)
 */
public class MnistReader
{
    private static final String TRAINING_IMAGES_FILE_NAME =
        "/train-images.idx3-ubyte";
    private static final String TRAINING_LABELS_FILE_NAME =
        "/train-labels.idx1-ubyte";
    private static final String TEST_IMAGES_FILE_NAME     =
        "/t10k-images.idx3-ubyte";
    private static final String TEST_LABELS_FILE_NAME     =
        "/t10k-labels.idx1-ubyte";


    public static List<MnistNumber> readTrainingSet(String mnistDir) throws IOException
    {
        return readSet(mnistDir + TRAINING_IMAGES_FILE_NAME, mnistDir + TRAINING_LABELS_FILE_NAME);
    }


    public static List<MnistNumber> readTestSet(String mnistDir) throws IOException
    {
        return readSet(mnistDir + TEST_IMAGES_FILE_NAME, mnistDir + TEST_LABELS_FILE_NAME);
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
