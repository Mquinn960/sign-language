package mquinn.sign_language.svm;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.SVM;
import org.opencv.ml.StatModel;

import java.io.File;
import java.util.Iterator;

import mquinn.sign_language.imaging.IFrame;
import mquinn.sign_language.processing.IFrameProcessor;

import static org.opencv.core.Core.meanStdDev;
import static org.opencv.core.Core.transpose;
import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.core.CvType.CV_32SC1;
import static org.opencv.core.CvType.CV_64FC1;

public class FrameClassifier implements IFrameProcessor {

    private SVM svm;
    private IFrame workingFrame;
    private MatOfPoint features;
    private LetterClass result;
    private Mat flatFeatures, results;

    public FrameClassifier(File xmlFile){

        flatFeatures = new Mat();
        results = new Mat();
        svm = SVM.create();

        svm.setType(SVM.C_SVC);
        svm.setKernel(SVM.RBF);

        svm.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER, 100, 1e-6));

        svm = SVM.load(xmlFile.getAbsolutePath());

    }

    @Override
    public IFrame process(IFrame inputFrame) {
        workingFrame = inputFrame;
        classify();

        return workingFrame;
    }

    private void classify(){

        flattenFeatures();
        flatFeatures.convertTo(flatFeatures, CvType.CV_32F);

        float response = svm.predict(flatFeatures);

        result = LetterClass.getLetter((int)response);
        workingFrame.setLetterClass(result);

        Log.d("DEBUG", "LETTER CLASS: " + result);
    }

    private boolean isEligibleToClassify() {

        if (!workingFrame.getFeatures().isEmpty()) {
            Iterator<MatOfPoint> allMatOfPoint = workingFrame.getFeatures().iterator();
            while (allMatOfPoint.hasNext()) {
                features = allMatOfPoint.next();
                return true;
            }
        }
        return false;
    }

    private void flattenFeatures(){

        flatFeatures = workingFrame.getHogDesc();
        flatFeatures = flatFeatures.reshape(1,1);

    }


//    Not currently used - to be implemented
//    private void pcaReduce () {
//        // features
//
//        Mat test = new Mat();
//
//        Mat mean = new Mat();
//        mean.convertTo(mean, CV_32FC1);
//
//        Mat vectors = new Mat();
//        vectors.convertTo(vectors, CV_32FC1);
//
//
//        Mat values = new Mat();
//        values.convertTo(values, CV_32FC1);
//
//
//        test = features.reshape(1,features.rows());
//        test.convertTo(test, CV_32FC1);
//
//
//
////        Core.PCACompute(test, mean, vectors, 10);
//
//        Core.PCACompute2(test, mean, vectors, values, 10);
//
//

//        Mat projectVec = new Mat();
//        projectVec.convertTo(projectVec, CV_32FC1);
//
//        Core.PCAProject(test, mean, vectors, projectVec);
//
//        Mat backProjectVec = new Mat();
//        backProjectVec.convertTo(backProjectVec, CV_32FC1);
//
//        Core.PCABackProject(test, mean, vectors, backProjectVec);
//
//        mean.release();
//        vectors.release();
//        projectVec.release();
//        backProjectVec.release();


//        Core.PCAProject(x, new Mat(), new Mat(), y);


///       return x;
//    }

}
