package mnist;

/**
 *  @author Marcin Mikosik (https://github.com/mikosik)
 */
public class Image {
  public final Size size;
  public final byte[] data;

  public Image(Size size, byte[] data) {
    if (size.count() != data.length) {
      throw new IllegalArgumentException("Image with size " + size
          + " should have data length = " + size.count());
    }
    this.size = size;
    this.data = data;
  }

  public float[] asFloatArray() {
    float[] result = new float[data.length];
    for (int i = 0; i < data.length; i++) {
      result[i] = data[i] / 255.0f;
    }
    return result;
  }
}