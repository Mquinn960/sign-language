package mquinn.sign_language.svm;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.Ml;
import org.opencv.ml.SVM;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;

import mquinn.sign_language.R;
import mquinn.sign_language.imaging.IFrame;
import mquinn.sign_language.processing.IFrameProcessor;

public class FrameClassifier implements IFrameProcessor {

    SVM svm;
    IFrame workingFrame;
    MatOfPoint features;
    LetterClass result;

    public FrameClassifier(File xmlFile){

        svm = SVM.create();

        svm.setType(SVM.C_SVC);
        svm.setKernel(SVM.LINEAR);
        svm.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER, 100, 1e-6));

        svm = SVM.load(xmlFile.getAbsolutePath());

        // Perform training
//        svm.train(trainingDataMat, Ml.ROW_SAMPLE, labelsMat);

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
        float response = svm.predict(features);
        result = LetterClass.getLetter((int)response);
        workingFrame.setLetterClass(result);
        Log.d("DEBUG", "LETTER CLASS: " + result);
    }

    private boolean isEligibleToClassify() {
        // SVM Prediction
        if (!workingFrame.getFeatures().isEmpty()) {
            Iterator<MatOfPoint> allMatOfPoint = workingFrame.getFeatures().iterator();
            while (allMatOfPoint.hasNext()) {
                if (allMatOfPoint.next().toList().size() == 20)
                    features = allMatOfPoint.next();
                    return true;
            }
        }
        return false;
    }

    private void flattenFeatures(){
        features.reshape(0,1);
    }

}
