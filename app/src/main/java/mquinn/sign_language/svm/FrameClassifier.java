package mquinn.sign_language.svm;

import android.util.Log;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.TermCriteria;
import org.opencv.ml.SVM;

import java.io.File;
import java.util.Iterator;

import mquinn.sign_language.imaging.IFrame;
import mquinn.sign_language.processing.IFrameProcessor;

public class FrameClassifier implements IFrameProcessor {

    SVM svm;
    IFrame workingFrame;
    MatOfPoint features;
    LetterClass result;
    Mat flatFeatures, results;

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
        flattenFeatures();
        flatFeatures.convertTo(flatFeatures, CvType.CV_32F);

        float response = svm.predict(flatFeatures);

        svm.predict(flatFeatures, results, 0);

        for (int row = 0; row < results.rows(); row++){
            for (int col = 0; col < results.cols(); col++) {
                Log.d("DEBUG", results.get(row, col).toString());
            }
        }

        result = LetterClass.getLetter((int)response);
        workingFrame.setLetterClass(result);
        Log.d("DEBUG", "LETTER CLASS: " + result);
    }

    private boolean isEligibleToClassify() {
        // SVM Prediction
        if (!workingFrame.getFeatures().isEmpty()) {
            Iterator<MatOfPoint> allMatOfPoint = workingFrame.getFeatures().iterator();
            while (allMatOfPoint.hasNext()) {
                MatOfPoint temp = allMatOfPoint.next();
                if (temp.toList().size() == 20){
                    features = temp;
                    return true;
                }
            }
        }
        return false;
    }

    private void flattenFeatures(){
        flatFeatures = features.reshape(1,1);
    }

}
