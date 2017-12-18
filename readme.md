# Java read Tensorflow model

Fixed version of Java MNIST Data Read Routines for playing with tensorflow tutorials. Displays the handwritten numbers properly. 

Reads a tensorflow model written in Python and trained for the MNIST data and does an accuracy check against the MNIST training data. Uses the model from the tensorflow tutorial [Deep MNIST for Experts ](https://www.tensorflow.org/get_started/mnist/pros). The python code in the root is the result of that tutorial with the code for saving the resulting trained model. This Java code reads that model and submits the MNIST data to it for predictions. The accuracy of the predictions are displayed. This Java code is based on the tutorial (I think) for the same at (Using Tensorflow models with Java)[https://www.youtube.com/watch?v=j3MZ0brQ0QE]. 

There are a couple of Java apps.

1: Package mnist - code for reading mnist data. Not mine, best I could find on github. Seems to work okay.

2: ShowMnistImages.java - reads MNIST data files and displays a random selection of digits.

3: LoadTensorFlowModel.java - reads MNIST data and submits images one at a time to the model, checks results. Code from the tutorial.

4: TensorFlowAtOnce.java - reads all 10,000 MNIST images and submits them all at once to the model, checks results. Works, but causes some serious pagefile swapping on my Windows machine.

5: TensorFlowLambda.java - same as tutorial code, I think, but with a lambda expression to submit the image to tensorflow.

6: TensorFlowLambdaMin.java - uses a real simple model that multiplies the input by 2. Someone along the lines of [Getting Started With TensorFlow ](https://www.tensorflow.org/get_started/get_started)

Uses the expert convolutional neural net model in the [Deep MNIST for Experts ](https://www.tensorflow.org/get_started/mnist/pros) tensorflow getting started guide.


   
