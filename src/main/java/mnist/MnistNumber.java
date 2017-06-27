package mnist;

/**
 *  @author Marcin Mikosik (https://github.com/mikosik)
 */
public class MnistNumber {
  public final Image image;
  public final byte label;

  public MnistNumber(Image image, byte label) {
    if (label < 0 || 9 < label) {
      throw new IllegalArgumentException();
    }
    this.image = image;
    this.label = label;
  }
}