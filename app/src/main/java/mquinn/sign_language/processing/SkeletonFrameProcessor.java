package mquinn.sign_language.processing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class SkeletonFrameProcessor implements IFrameProcessor {

    private List<MatOfPoint> featureList = new ArrayList<>();
    private MatOfPoint features = new MatOfPoint();
    private Mat greyScale = new Mat();

    @Override
    public IFrame process(IFrame inputFrame) {

        featureList.clear();

        Imgproc.cvtColor(inputFrame.getDownSampledMat(), greyScale, Imgproc.COLOR_RGBA2GRAY);


        // Test
        Mat mask = Mat.zeros(greyScale.rows(), greyScale.cols(), CvType.CV_8UC1);
        Imgproc.drawContours(mask, inputFrame.getContours(), -1, new Scalar(255), -1);


        Imgproc.goodFeaturesToTrack(greyScale, features, 50, 0.01, 5, mask);

        Core.multiply(features, new Scalar(4, 4), features);

        featureList.add(features);
        inputFrame.setFeatures(featureList);

        return inputFrame;

    }

}
