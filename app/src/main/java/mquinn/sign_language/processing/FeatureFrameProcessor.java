package mquinn.sign_language.processing;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class FeatureFrameProcessor implements IFrameProcessor {

    private List<MatOfPoint> featureList = new ArrayList<>();
    private MatOfPoint features = new MatOfPoint();
    private Mat greyScale = new Mat();
    private Mat featureInput = new Mat();
    private FeatureTarget featureTarget;

    public FeatureFrameProcessor(FeatureTarget inputFeatureTarget) {
        featureTarget = inputFeatureTarget;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        featureList.clear();

        Imgproc.cvtColor(inputFrame.getDownSampledMat(), greyScale, Imgproc.COLOR_RGBA2GRAY);

        switch (featureTarget){
            case SKELETON:
                featureInput = inputFrame.getSkeleton();
            case CONTOUR_MASK:
                featureInput = inputFrame.getMaskedImage();
            case CONTOUR_OUTLINE:
                //TODO: outline method
                // featureInput = inputFrame.getOutline();
            default:
                featureInput = inputFrame.getSkeleton();
        }

        Imgproc.goodFeaturesToTrack(greyScale, features, 30, 0.01, 10, featureInput);

        featureList.add(features);

        inputFrame.setFeatures(featureList);

        return inputFrame;

    }

    public void setFeatureTarget(FeatureTarget inputFeatureTarget){
        featureTarget = inputFeatureTarget;
    }


}
