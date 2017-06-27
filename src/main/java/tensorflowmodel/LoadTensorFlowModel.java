package tensorflowmodel;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;
import mnist.MnistNumber;
import mnist.MnistReader;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;

/**
 * Hello world!
 *
 */
public class LoadTensorFlowModel 
{
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param args
     * @throws IOException
     */
    public static void main( String[] args ) throws IOException
    {
        new LoadTensorFlowModel().run();
    }
    private void run() throws IOException {
        try
        {
            List<MnistNumber> testSet = MnistReader.readTestSet("/Users/karln/.spyder-py3/MNIST_data");
            System.out.println(TensorFlow.version());
            try (SavedModelBundle b = SavedModelBundle.load("/karl/model3", "serve")) {
                Session s = b.session();
                
                int cp= 0;
                for ( int i = 0; i < testSet.size(); ++i ) {
                    FloatBuffer fb = FloatBuffer.allocate(784);
                    for( byte bt: testSet.get(i).image.data) {
                        fb.put((bt & 0xFF )/255.0f);
                    }
                    fb.rewind();
                    
                    float[] keep_prob_array = new float[1024];
                    Arrays.fill(keep_prob_array, 1f);
                    
                    Tensor x = Tensor.create(new long[] {784}, fb);
                    Tensor keep_prob = Tensor.create(new long[] {1, 1024}, FloatBuffer.wrap(keep_prob_array));
                    
                    Tensor result = s.runner()
                        .feed("x", x)
                        .feed("keep_prob", keep_prob)
                        .fetch("y_conv")
                        .run().get(0);
                    
                    float[][] m = new float[1][10];
                    //m[0] = new float[10];
                    //Arrays.fill(m[0], 0);
                    
                    float[][] matrix = result.copyTo(m);
                    float maxVal = 0;
                    
                    int inc = 0;
                    int predict = -1;
                    for(float val : matrix[0] ) {
                        if(val > maxVal) {
                            predict = inc;
                            maxVal = val;
                        }
                        inc++; 
                    }
                    
                    if ( predict == testSet.get(i).label ) {
                        cp++;
                    }
                }
                System.out.println("XXX");
                System.out.println(cp);
                System.out.println(((float)cp)/((float)testSet.size()));
              }

        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
