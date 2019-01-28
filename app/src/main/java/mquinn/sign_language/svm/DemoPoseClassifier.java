package mquinn.sign_language.svm;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.ml.SVM;

import java.io.File;

public class DemoPoseClassifier {

//    private static Mat trainingImages;
//    private static Mat trainingLabels;
//    private static Mat trainingData;
//    private static Mat classes;
//
//    public DemoPoseClassifier() {
//        trainingImages = new Mat();
//        trainingLabels = new Mat();
//        trainingData = new Mat();
//        classes = new Mat();
//    }
//
//    public void trainOffline() {
//
//        trainPositive();
//        trainNegative();
//        train();
//
//    }
//
//    protected static void train() {
//        trainingImages.copyTo( trainingData );
//        trainingData.convertTo( trainingData, CvType.CV_32FC1 );
//        trainingLabels.copyTo( classes );
//
//        SVM clasificador = SVM.create();
//        clasificador.setKernel( SVM.LINEAR );
//        clasificador = new SVM( trainingData, classes, new Mat(), new Mat() );
//
//        clasificador.save( XML );
//    }
//
//    protected static void trainPositive() {
//        for ( File file : new File( PATH_POSITIVE ).listFiles() ) {
//            Mat img = getMat( file.getAbsolutePath() );
//            trainingImages.push_back( img.reshape( 1, 1 ) );
//            trainingLabels.push_back( Mat.ones( new Size( 1, 1 ), CvType.CV_32FC1 ) );
//        }
//    }
//
//    protected static void trainNegative() {
//        for ( File file : new File( PATH_NEGATIVE ).listFiles() ) {
//            Mat img = getMat( file.getAbsolutePath() );
//            trainingImages.push_back( img.reshape( 1, 1 ) );
//            trainingLabels.push_back( Mat.zeros( new Size( 1, 1 ), CvType.CV_32FC1 ) );
//        }
//    }
//
//    protected static Mat getMat( String path ) {
//        Mat img = new Mat();
//        Mat con = Highgui.imread( path, Highgui.CV_LOAD_IMAGE_GRAYSCALE );
//        con.convertTo( img, CvType.CV_32FC1, 1.0 / 255.0 );
//        return img;
//    }

}
