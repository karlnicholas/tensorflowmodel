package mnist;

/**
 *  @author Marcin Mikosik (https://github.com/mikosik)
 */
public class Size {
  public final int width;
  public final int height;

  public Size(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public int count() {
    return width * height;
  }

  public String toString() {
    return "Size(" + width + "," + height + ")";
  }
}