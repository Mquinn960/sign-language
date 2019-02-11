package mquinn.sign_language.svm;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.SVM;

import java.io.File;
import java.util.Iterator;

import mquinn.sign_language.imaging.IFrame;
import mquinn.sign_language.processing.IFrameProcessor;

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
        svm.setKernel(SVM.LINEAR);
        svm.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER, 100, 1e-6));

        svm = SVM.load(xmlFile.getAbsolutePath());

    }

    @Override
    public IFrame process(IFrame inputFrame) {
        workingFrame = inputFrame;
        if (isEligibleToClassify()) {
            classify();
        }

        return workingFrame;
    }

    private void classify(){

        Mat x = workingFrame.getSiftFeatures();

        flatFeatures = x.reshape(1,1);

        flatFeatures.convertTo(flatFeatures, CvType.CV_32F);

        float response = svm.predict(flatFeatures);

        svm.predict(flatFeatures, results, 0);

        result = LetterClass.getLetter((int)response);
        workingFrame.setLetterClass(result);

        Imgproc.putText(workingFrame.getRGBA(),
                workingFrame.getLetterClass().toString(),
                new Point(100,180),
                Core.FONT_HERSHEY_PLAIN,
                2,
                new Scalar(255,255,255),
                2);

        Log.d("DEBUG", "LETTER CLASS: " + result);
    }

//    private void classify(){
//        flattenFeatures();
//        flatFeatures.convertTo(flatFeatures, CvType.CV_32F);
//
//        float response = svm.predict(flatFeatures);
//
//        svm.predict(flatFeatures, results, 0);
//
//        result = LetterClass.getLetter((int)response);
//        workingFrame.setLetterClass(result);
//
//        Imgproc.putText(workingFrame.getRGBA(),
//                workingFrame.getLetterClass().toString(),
//                new Point(100,180),
//                Core.FONT_HERSHEY_PLAIN,
//                2,
//                new Scalar(255,255,255),
//                2);
//
//        Log.d("DEBUG", "LETTER CLASS: " + result);
//    }

//    private boolean isEligibleToClassify() {
//        // SVM Prediction
//        if (!workingFrame.getFeatures().isEmpty()) {
//            Iterator<MatOfPoint> allMatOfPoint = workingFrame.getFeatures().iterator();
//            while (allMatOfPoint.hasNext()) {
//                MatOfPoint temp = allMatOfPoint.next();
//                if (temp.toList().size() == 20){
//                    features = temp;
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    private boolean isEligibleToClassify() {
        // SVM Prediction
        if (workingFrame.getSiftFeatures().rows() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void flattenFeatures(){
        flatFeatures = features.reshape(1,1);
    }

}
