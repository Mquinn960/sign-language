package mquinn.sign_language.svm;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.Ml;
import org.opencv.ml.SVM;
import org.opencv.ml.TrainData;

public class OfflineSVMTrainer {


    public void trainOffline() {



        // Feature labels
        int[] labels = { 1, -1, -1, -1 };

        Mat labelsMat = new Mat(4, 1, CvType.CV_32SC1);
        labelsMat.put(0, 0, labels);



        // Feature matrix
        float[] trainingData = { 501, 10, 255, 10, 501, 255, 10, 501 };

        Mat trainingDataMat = new Mat(4, 2, CvType.CV_32FC1);
        trainingDataMat.put(0, 0, trainingData);


        // Create SVM and set config
        SVM svm = SVM.create();

        svm.setType(SVM.C_SVC);
        svm.setKernel(SVM.LINEAR);
        svm.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER, 100, 1e-6));


        // Perform training
        svm.train(trainingDataMat, Ml.ROW_SAMPLE, labelsMat);



        // Data for visual representation
        int width = 512, height = 512;
        Mat image = Mat.zeros(height, width, CvType.CV_8UC3);



        // Get SVM prediction and plot

        byte[] imageData = new byte[(int) (image.total() * image.channels())];
        Mat sampleMat = new Mat(1, 2, CvType.CV_32F);
        float[] sampleMatData = new float[(int) (sampleMat.total() * sampleMat.channels())];
        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                sampleMatData[0] = j;
                sampleMatData[1] = i;
                sampleMat.put(0, 0, sampleMatData);
                float response = svm.predict(sampleMat);

                if (response == 1) {
                    imageData[(i * image.cols() + j) * image.channels()] = 0;
                    imageData[(i * image.cols() + j) * image.channels() + 1] = (byte) 255;
                    imageData[(i * image.cols() + j) * image.channels() + 2] = 0;
                } else if (response == -1) {
                    imageData[(i * image.cols() + j) * image.channels()] = (byte) 255;
                    imageData[(i * image.cols() + j) * image.channels() + 1] = 0;
                    imageData[(i * image.cols() + j) * image.channels() + 2] = 0;
                }
            }
        }

        // Show points on image

        image.put(0, 0, imageData);


        // Show support vectors

        Mat sv = svm.getUncompressedSupportVectors();
        float[] svData = new float[(int) (sv.total() * sv.channels())];
        sv.get(0, 0, svData);
        for (int i = 0; i < sv.rows(); ++i) {
            Imgproc.circle(image, new Point(svData[i * sv.cols()], svData[i * sv.cols() + 1]), 6,
                    new Scalar(128, 128, 128), -1, 1, 0);
        }

        // Save the output image
        Imgcodecs.imwrite("result.png", image); // save the image

    }

}
