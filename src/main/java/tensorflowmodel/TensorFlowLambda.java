package tensorflowmodel;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
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
public class TensorFlowLambda 
{    
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param args
     * @throws IOException
     */
    public static void main( String[] args ) throws IOException
    {
        new TensorFlowLambda().run();
    }
    private void run() throws IOException {
        System.out.println(TensorFlow.version());        

        try (SavedModelBundle b = SavedModelBundle.load("/karl/model3", "serve")) {

            Session sess = b.session();
            float[] keep_prob_array = new float[1024];
            Arrays.fill(keep_prob_array, 1f);
            Tensor keep_prob = Tensor.create(new long[] {1, 1024}, FloatBuffer.wrap(keep_prob_array));

            Function<MnistNumber, Boolean> runPrediction = (mnistNumber) -> {
                FloatBuffer fb = FloatBuffer.allocate(784);
                for( byte bt: mnistNumber.image.data) {
                    fb.put((bt & 0xFF )/255.0f);
                }
                fb.rewind();
                Tensor input = Tensor.create(new long[] {784}, fb);
        
                float[][]matrix = sess.runner()
                    .feed("x", input)
                    .feed("keep_prob", keep_prob)
                    .fetch("y_conv")
                    .run()
                    .get(0)
                    .copyTo(new float[1][10]);
        
                int prediction = 0;
                float maxVal = matrix[0][0];
                for ( int i = 1; i < matrix[0].length; ++i ) {
                    float val = matrix[0][i];
                    if( val > maxVal) {
                        prediction = i;
                        maxVal = val;
                    }
                }
                return prediction == mnistNumber.label; 
            };

            Long r = MnistReader.readTestSet().parallelStream()
                .map(runPrediction)
                .filter(Boolean::booleanValue)
                .collect(Collectors.counting());

            System.out.println(r);
        }                
    }
}
