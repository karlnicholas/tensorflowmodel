package mnist;

import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 *  @author Marcin Mikosik (https://github.com/mikosik)
 */
public class MnistWriter {
  public static void saveMnist(List<MnistNumber> trainingNumbers, String outputDir)
      throws IOException {
    for (int i = 0; i < trainingNumbers.size(); i++) {
      save(trainingNumbers.get(i), i, outputDir);
    }
  }

  private static void save(MnistNumber number, int i, String outputDir) throws IOException {
    Size size = number.image.size;
    BufferedImage jImage = new BufferedImage(size.width, size.height, TYPE_BYTE_GRAY);
    WritableRaster raster = jImage.getRaster();
    raster.setPixels(0, 0, size.width, size.height, convert(number));
    String format = "bmp";
    String name = String.format("%05d", i) + "_" + number.label + "." + format;
    ImageIO.write(jImage, format, new File(outputDir, name));
  }

  private static int[] convert(MnistNumber number) {
    byte[] data = number.image.data;
    int[] array = new int[data.length];
    for (int i = 0; i < data.length; i++) {
      array[i] = data[i];
    }
    return array;
  }
}