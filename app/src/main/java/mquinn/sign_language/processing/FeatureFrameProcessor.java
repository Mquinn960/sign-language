package mquinn.sign_language.processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.objdetect.HOGDescriptor;

import java.util.ArrayList;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class FeatureFrameProcessor implements IFrameProcessor {

    private List<MatOfPoint> featureList = new ArrayList<>();
    private MatOfPoint features = new MatOfPoint();
    private Mat greyScale = new Mat();
    private Mat featureInput = new Mat();
    private DetectionMethod detectionMethod;

    private MatOfFloat hogDesc = new MatOfFloat();
    private MatOfPoint hogLoc = new MatOfPoint();

//    private List<Mat> hogList = new ArrayList<Mat>();
//    private MatOfFloat hogDescFull = new MatOfFloat();

    private HOGDescriptor hog;

    public FeatureFrameProcessor(DetectionMethod inputDetectionMethod) {
        detectionMethod = inputDetectionMethod;

        hog = new HOGDescriptor(
                new Size(32,32), //winSize
                new Size(16,16), //blocksize
                new Size(8,8), //blockStride,
                new Size(8,8), //cellSize,
                9, //nbins,
                1, //derivAper,
                -1, //winSigma,
                HOGDescriptor.L2Hys, //histogramNormType,
                0.2, //L2HysThresh,
                true,//gammal correction,
                HOGDescriptor.DEFAULT_NLEVELS,//nlevels=64
                true);
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        featureList.clear();
        hogDesc.release();
        hogLoc.release();

        switch (detectionMethod){
            case SKELETON:

                featureInput = inputFrame.getSkeleton();

                break;
            case CONTOUR_MASK:

                featureInput = inputFrame.getWindowMask();

                break;
            case CANNY_EDGES:

                featureInput = inputFrame.getCannyEdgeMask();

                break;
            default:
                featureInput = inputFrame.getMaskedImage();
                break;
        }

//        // COMBO
//        hog.compute(inputFrame.getSkeleton(), hogDesc1, new Size(32,32),new Size(16,16), hogLoc1);
//        hog.compute(inputFrame.getCannyEdgeMask(), hogDesc2, new Size(32,32),new Size(16,16), hogLoc2);
//
//        hogList.add((Mat)hogDesc1);
//        hogList.add((Mat)hogDesc2);
//
//        Core.vconcat(hogList, hogDescFull);
//
//        inputFrame.setHogDesc(hogDescFull);

        hog.compute(featureInput, hogDesc, new Size(32,32),new Size(16,16), hogLoc);
        inputFrame.setHogDesc(hogDesc);

//        featureList.add(features);
//        inputFrame.setFeatures(featureList);

        return inputFrame;

    }

    public void setDetectionMethod(DetectionMethod inputDetectionMethod){
        detectionMethod = inputDetectionMethod;
    }

}
