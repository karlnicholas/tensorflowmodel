package tensorflowmodel;

import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.swing.SwingUtilities;
import mnist.MnistNumber;
import mnist.MnistReader;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 * 
 *  @author karln
 *  @version Jun 27, 2017
 */
public class ShowMnistImages extends JFrame
{
    private List<MnistNumber> testSet;

    // ----------------------------------------------------------
    /**
     * Create a new ShowMnistImages object.
     */
    public ShowMnistImages() {
        try
        {
            testSet = MnistReader.readTestSet("/Users/karln/.spyder-py3/MNIST_data");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        setSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public void paint(Graphics g)
    {
        for ( int x = 0; x < 32*15; x += 32  ) {
            for ( int y = 0; y < 32*14; y += 32 ) {
                MnistNumber mnistNumber; 
                int rand = (int)(Math.random()*10000.0);
                mnistNumber = testSet.get(rand);
                int width = mnistNumber.image.size.width;
                int height = mnistNumber.image.size.height;
                BufferedImage image = new BufferedImage(width+4, height+4, java.awt.image.BufferedImage.TYPE_INT_RGB);
                Graphics2D gi = image.createGraphics();
                gi.setStroke(new BasicStroke(2.0f));
                gi.drawRect(2,2, width, height);
                for (int w = 2; w < width+2; w++) {
                    for (int h = 2; h < height+2; h++) {
                        //System.err.println("byteArray[]: " + Byte.toUnsignedInt(values[w*h+h]));
                        //image.setRGB(w, h, Byte.toUnsignedInt(values[h*w+h]));
    //                    int v = 255-(255*(h*w+h)/(height*width+height));
                        int loc = ((h-2)*width)+(w-2);
                        int v = 255-Byte.toUnsignedInt(mnistNumber.image.data[loc]);
                        int rgb = v << 16 | v << 8 | v; 
                        image.setRGB(w, h, rgb);
                    }
                }
                g.drawImage(image, x+8, y+height+4, null);
            }
        }

    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param arg
     */
    public static void main(String arg[])
    {
        SwingUtilities.invokeLater( ShowMnistImages::new );
    }

}
