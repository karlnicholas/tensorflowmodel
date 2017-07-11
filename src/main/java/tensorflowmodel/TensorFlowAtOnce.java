package tensorflowmodel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;
import mnist.MnistNumber;
import mnist.MnistReader;
import org.tensorflow.DataType;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;

/**
 * Hello world!
 *
 */
public class TensorFlowAtOnce 
{    
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param args
     * @throws IOException
     */
    public static void main( String[] args ) throws IOException
    {
        new TensorFlowAtOnce().run();
    }
    private void run() throws IOException {
        System.out.println(TensorFlow.version());        

        try (SavedModelBundle b = SavedModelBundle.load("/karl/model3", "serve")) {

            Session sess = b.session();
            float[] keep_prob_array = new float[1024];
            Arrays.fill(keep_prob_array, 1f);
            Tensor keep_prob = Tensor.create(new long[] {1, 1024}, FloatBuffer.wrap(keep_prob_array));

            List<MnistNumber> numbers = MnistReader.readTestSet("/Users/karln/.spyder-py3/MNIST_data");
            int NUM_PRED = numbers.size();

            ByteBuffer byteBuffer = ByteBuffer.allocate(NUM_PRED*784*Float.BYTES).order(ByteOrder.LITTLE_ENDIAN);
            for ( MnistNumber mnistNumber: numbers ) {
                for( byte bt: mnistNumber.image.data) {
                    float v = (bt & 0xFF )/255.0f;
                    byteBuffer.putFloat(v);
                }
            }

            byteBuffer.rewind();

            Tensor input = Tensor.create(DataType.FLOAT, new long[] {NUM_PRED, 784}, byteBuffer);
    
            float[][]results = sess.runner()
                .feed("x", input)
                .feed("keep_prob", keep_prob)
                .fetch("y_conv")
                .run()
                .get(0)
                .copyTo(new float[NUM_PRED][10]);

            int correct = 0;
            for ( int p = 0; p < NUM_PRED; ++p ) {
                int prediction = 0;
                float maxVal = results[p][0];
                for ( int i = 1; i < results[p].length; ++i ) {
                    float val = results[p][i];
                    if( val > maxVal) {
                        prediction = i;
                        maxVal = val;
                    }
                }
                if ( prediction == numbers.get(p).label ) {
                    correct++;
                }
            }
            System.out.println(correct + " " + correct/(float)NUM_PRED);
        }                
    }
}
