package mnist;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *  @author Marcin Mikosik (https://github.com/mikosik)
 */
public class MnistLabelsReader implements AutoCloseable {
  private static final int FILE_MAGIC_NUMBER = 0x00000801;

  private final DataInputStream data;
  public final int labelsCount;
  private int readCount;

  public static MnistLabelsReader open(String filePath) throws IOException {
    DataInputStream data = openDataStream(filePath);
    if (FILE_MAGIC_NUMBER != data.readInt()) {
      throw new RuntimeException("Incorrect magic number");
    }
    int imageCount = data.readInt();
    return new MnistLabelsReader(data, imageCount);
  }

  public MnistLabelsReader(DataInputStream data, int labelsCount) {
    this.data = data;
    this.labelsCount = labelsCount;
    this.readCount = 0;
  }

  public boolean hasNext() {
    return readCount < labelsCount;
  }

  public byte next() throws IOException {
    readCount++;
    return data.readByte();
  }

  private static DataInputStream openDataStream(String filePath) throws FileNotFoundException {
    return new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
  }

  public void close() throws IOException {
    data.close();
  }
}