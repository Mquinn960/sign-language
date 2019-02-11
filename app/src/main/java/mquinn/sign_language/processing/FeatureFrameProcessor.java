package mquinn.sign_language.processing;

import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;
import org.opencv.xfeatures2d.SIFT;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class FeatureFrameProcessor implements IFrameProcessor {

    private List<MatOfPoint> featureList = new ArrayList<>();
    private MatOfPoint features = new MatOfPoint();
    private Mat greyScale = new Mat();
    private Mat featureInput = new Mat();
    private DetectionMethod detectionMethod;

    private MatOfKeyPoint keypoints = new MatOfKeyPoint();
    private Mat descriptors = new Mat();

    private SIFT siftExtractor = SIFT.create(100, 3, 0.04, 10, 1.6);

    public FeatureFrameProcessor(DetectionMethod inputDetectionMethod) {
        detectionMethod = inputDetectionMethod;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        featureList.clear();

        switch (detectionMethod){
            case SKELETON:

                featureInput = inputFrame.getSkeleton();
                Imgproc.goodFeaturesToTrack(featureInput, features, 20, 0.005, 8);

                break;
            case CONTOUR_MASK:

                featureInput = inputFrame.getMaskedImage();
                Imgproc.cvtColor(inputFrame.getDownSampledMat(), greyScale, Imgproc.COLOR_RGBA2GRAY);
                Imgproc.goodFeaturesToTrack(greyScale, features, 20, 0.01, 10, featureInput,3,3);

                break;
            case CANNY_EDGES:

                featureInput = inputFrame.getCannyEdgeMask();
                Imgproc.goodFeaturesToTrack(featureInput, features, 20, 0.01, 10);

                siftExtractor.detect(inputFrame.getCannyEdgeMask(), keypoints);
                siftExtractor.compute(inputFrame.getCannyEdgeMask(), keypoints, descriptors);
                inputFrame.setSiftFeatures(descriptors);

                break;
            default:
                featureInput = inputFrame.getMaskedImage();
                break;
        }

//        Log.d("DEBUG","Corners: " + features.toList().size());

        featureList.add(features);
        inputFrame.setFeatures(featureList);

        return inputFrame;

    }

    public void setDetectionMethod(DetectionMethod inputDetectionMethod){
        detectionMethod = inputDetectionMethod;
    }

}
