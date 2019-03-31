package mquinn.sign_language.processing;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.objdetect.HOGDescriptor;

import java.util.ArrayList;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class FeatureFrameProcessor implements IFrameProcessor {

    private List<MatOfPoint> featureList = new ArrayList<>();
    private Mat featureInput = new Mat();
    private DetectionMethod detectionMethod;

    private MatOfFloat hogDesc = new MatOfFloat();
    private MatOfPoint hogLoc = new MatOfPoint();

    private HOGDescriptor hog;

    public FeatureFrameProcessor(DetectionMethod inputDetectionMethod) {
        detectionMethod = inputDetectionMethod;

        // TODO: try window size of 100
        hog = new HOGDescriptor(
                new Size(32,32), // Window Size
                new Size(16,16), // Block Size
                new Size(8,8), // Block Stride,
                new Size(8,8), // Cell Size,
                9,
                1,
                -1,
                HOGDescriptor.L2Hys, // Histogram Norm Type,
                0.2,
                true,
                HOGDescriptor.DEFAULT_NLEVELS, // nLevels
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
                featureInput = inputFrame.getWindowMask();
                break;
        }

        hog.compute(featureInput, hogDesc, new Size(32,32),new Size(16,16), hogLoc);
        inputFrame.setHogDesc(hogDesc);

        return inputFrame;

    }

    public void setDetectionMethod(DetectionMethod inputDetectionMethod){
        detectionMethod = inputDetectionMethod;
    }

}
