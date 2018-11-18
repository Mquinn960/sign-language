package mquinn.sign_language.processing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class SkeletonFrameProcessor implements IFrameProcessor {

    private List<MatOfPoint> featureList;
    private MatOfPoint features;
    private Mat greyScale;

    public SkeletonFrameProcessor(){
        featureList = new ArrayList<>();
        features = new MatOfPoint();
        greyScale = new Mat();
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        featureList.clear();

        Imgproc.cvtColor(inputFrame.getDownSampledMat(), greyScale, Imgproc.COLOR_RGBA2GRAY);
        Imgproc.goodFeaturesToTrack(greyScale, features, 100, 0.01, 5);

        Core.multiply(features, new Scalar(4, 4), features);

        featureList.add(features);
        inputFrame.setFeatures(featureList);

        return inputFrame;

    }

}
