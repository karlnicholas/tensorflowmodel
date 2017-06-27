package mnist;

import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *  @author Marcin Mikosik (https://github.com/mikosik)
 */
public class MNISTWriteImage {
    public static String trainingImagePath = "C:/Users/karln/.spyder-py3/MNIST_data/train-images-idx3-ubyte";
    public static FileInputStream trainingImagesStream;
    public static int trainingImagesMagicNumber, trainingLabelsMagicNumber, testImagesMagicNumber, testLabelsMagicNumber;
    public static int trainingImagesCount, trainingLabelsCount, testImagesCount, testLabelsCount;
    public static int trainingImagesWidth, trainingImagesHeight, testImagesWidth, testImagesHeight;
    public static int trainingImagesRead = 0, testImagesRead = 0;
    
    /**
     * Will close the stream if it's already open. 
     */
    public static void openTrainingImages() {
        try {
            if(trainingImagesStream != null) trainingImagesStream.close();
        } catch (IOException ex) {
            Logger.getLogger(MNISTWriteImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            trainingImagesStream = new FileInputStream(trainingImagePath);
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: FileNotFoundException for MNIST Training Images");
        }
    }
    
    /**
     * Read 4 bytes from a FileInputStream
     * @return 
     */
    public static byte[] read4Bytes(FileInputStream fstream) {
        //the magic number is 4 bytes
        byte[] bytes = new byte[4];
        try {
            fstream.read(bytes);
        } catch (IOException ex) {
            Logger.getLogger(MNISTWriteImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bytes;
    }
    
    /**
     * This method reads the magic number at the start of an idx file. 
     * @return 
     */
    public static void readTrainingImagesHeader() {
        openTrainingImages();
        //the magic number is 4 bytes
        byte[] magic_number = new byte[4];
        byte[] images_count = new byte[4];
        byte[] image_width = new byte[4];
        byte[] image_height = new byte[4];
        try {
            trainingImagesStream.read(magic_number);
            trainingImagesStream.read(images_count);
            trainingImagesStream.read(image_width);
            trainingImagesStream.read(image_height);
            ByteBuffer bb = ByteBuffer.wrap(magic_number);
            trainingImagesMagicNumber = bb.getInt();
            bb = ByteBuffer.wrap(images_count);
            trainingImagesCount = bb.getInt();
            bb = ByteBuffer.wrap(image_width);
            trainingImagesWidth = bb.getInt();
            bb = ByteBuffer.wrap(image_height);
            trainingImagesHeight = bb.getInt();
        } catch (IOException ex) {
            Logger.getLogger(MNISTWriteImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void readTrainingImage() throws IOException {
        readTrainingImagesHeader();
        byte[] bytes = new byte[trainingImagesWidth*trainingImagesHeight];
        trainingImagesStream.read(bytes);
        int[] pixels = new int[trainingImagesWidth*trainingImagesHeight];
        BufferedImage image = new BufferedImage(trainingImagesWidth, trainingImagesHeight, TYPE_BYTE_GRAY);
        for (int w = 0; w < trainingImagesWidth; w++) {
            for (int h = 0; h < trainingImagesHeight; h++) {
                //System.err.println("byteArray[]: " + Byte.toUnsignedInt(values[w*h+h]));
                //image.setRGB(w, h, Byte.toUnsignedInt(values[h*w+h]));
                pixels[h*w+h] = Byte.toUnsignedInt(bytes[h*w+h]);
            }
        }
        image.getRaster().setPixels(0, 0, trainingImagesWidth, trainingImagesHeight, pixels);
        File outFile = new File("trainingImage" + trainingImagesRead +++ ".bmp");
        ImageIO.write(image, "bmp", outFile);
    }
    
    public static void readTestImages() {
    
    }
    
    public static void readTrainingLabels() {
    
    }
    
    public static void readTestLabels() {
    
    }
}