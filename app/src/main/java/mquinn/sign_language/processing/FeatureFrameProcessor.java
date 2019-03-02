package mquinn.sign_language.processing;

import android.util.Log;

import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.xfeatures2d.SIFT;
import org.opencv.xfeatures2d.SURF;
import org.opencv.ximgproc.Ximgproc;

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
    private MatOfDouble weights = new MatOfDouble();

    private Mat huMoments = new Mat();
    private Moments mom = new Moments();
    private Mat huNorm = new Mat();

    private MatOfFloat hogDesc = new MatOfFloat();
    private MatOfPoint hogLoc = new MatOfPoint();

    private MatOfKeyPoint siftKeys = new MatOfKeyPoint();
    private Mat siftDesc = new Mat();

    public FeatureFrameProcessor(DetectionMethod inputDetectionMethod) {
        detectionMethod = inputDetectionMethod;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        featureList.clear();

        switch (detectionMethod){
            case SKELETON:

                featureInput = inputFrame.getSkeleton();
                Imgproc.goodFeaturesToTrack(featureInput, features, 15, 0.001, 5);

                mom = Imgproc.moments(inputFrame.getSkeleton());
                Imgproc.HuMoments(mom, huMoments);

                huMoments.copyTo(huNorm);

                for(int i = 0; i < 7; i++)
                {
                    double x = huMoments.get(i,0)[0];
                    double y = -1 * Math.copySign(1.0, x) * Math.log10(Math.abs(x));
                    huNorm.put(i,0, y);
                }

                break;
            case CONTOUR_MASK:

                featureInput = inputFrame.getMaskedImage();
                Imgproc.cvtColor(inputFrame.getDownSampledMat(), greyScale, Imgproc.COLOR_RGBA2GRAY);
                Imgproc.goodFeaturesToTrack(greyScale, features, 15, 0.001, 5, featureInput,3,3);

                break;
            case CANNY_EDGES:

                featureInput = inputFrame.getCannyEdgeMask();
                Imgproc.goodFeaturesToTrack(featureInput, features, 15, 0.001, 5);

                mom = Imgproc.moments(inputFrame.getCannyEdgeMask());
                Imgproc.HuMoments(mom, huMoments);

                huMoments.copyTo(huNorm);

                for(int i = 0; i < 7; i++)
                {
                    double x = huMoments.get(i,0)[0];
                    double y = -1 * Math.copySign(1.0, x) * Math.log10(Math.abs(x));
                    huNorm.put(i,0, y);
                }

                HOGDescriptor hog = new HOGDescriptor(
                    new Size(20,20), //winSize
                    new Size(10,10), //blocksize
                    new Size(5,5), //blockStride,
                    new Size(10,10), //cellSize,
                    9, //nbins,
                    1, //derivAper,
                    -1, //winSigma,
                    0, //histogramNormType,
                    0.2, //L2HysThresh,
                    true,//gammal correction,
                    64,//nlevels=64
                        true);


//                hog.compute(inputFrame.getWindowMask(), hogDesc, new Size(32,32),new Size(0,0), hogLoc);


                hog.compute(inputFrame.getCannyEdgeMask(), hogDesc, new Size(32,32),new Size(0,0), hogLoc);


                inputFrame.setHogDesc(hogDesc);

//                SURF a = SURF.create();
//                SIFT b = SIFT.create(200);

//                b.detectAndCompute(inputFrame.getWindowMask(), new Mat(), siftKeys, siftDesc);




//                MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
//                FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.SURF);
//                System.out.println("Detecting key points...");
//                featureDetector.detect(objectImage, objectKeyPoints);
//                KeyPoint[] keypoints = objectKeyPoints.toArray();
//                System.out.println(keypoints);
//
//                MatOfKeyPoint objectDescriptors = new MatOfKeyPoint();
//                DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SURF);
//                System.out.println("Computing descriptors...");
//                descriptorExtractor.compute(objectImage, objectKeyPoints, objectDescriptors);

                break;
            default:
                featureInput = inputFrame.getMaskedImage();
                break;
        }

        inputFrame.setHuMomentFeat(huNorm);
        featureList.add(features);
        inputFrame.setFeatures(featureList);

        return inputFrame;

    }

    public void setDetectionMethod(DetectionMethod inputDetectionMethod){
        detectionMethod = inputDetectionMethod;
    }

}
