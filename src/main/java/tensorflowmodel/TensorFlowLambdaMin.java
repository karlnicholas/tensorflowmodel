package tensorflowmodel;

import java.io.IOException;
import java.nio.FloatBuffer;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;

/**
 * Hello world!
 *
 */
public class TensorFlowLambdaMin 
{    
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param args
     * @throws IOException
     */
    public static void main( String[] args ) throws IOException
    {
        // good idea to print the version number, 1.2.0 as of this writing
        System.out.println(TensorFlow.version());        
        final int NUM_PREDICTIONS = 1;

        // load the model Bundle
        try (SavedModelBundle b = SavedModelBundle.load("/tmp/model", "serve")) {

            // create the session from the Bundle
            Session sess = b.session();
            // create an input Tensor, value = 2.0f
            Tensor x = Tensor.create(
                new long[] {NUM_PREDICTIONS}, 
                FloatBuffer.wrap( new float[] {2.0f} ) 
            );
            
            // run the model and get the result, 4.0f.
            float[] ys = sess.runner()
                .feed("x", x)
                .fetch("y")
                .run()
                .get(0)
                .copyTo(new float[NUM_PREDICTIONS]);

            // print out the result(s).
            for ( float y: ys ) {
                System.out.println(y);
            }
        }                
    }
}
