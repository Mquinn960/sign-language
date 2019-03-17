package mquinn.sign_language.processing;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class CannyEdgeFrameProcessor implements IFrameProcessor {

    private Mat croppedMask = new Mat();
    private Mat cannyEdgeMask = new Mat();
    private Mat hierarchy = new Mat();
    private List<MatOfPoint> cannyEdges = new ArrayList<>();

    @Override
    public IFrame process(IFrame inputFrame) {

        cannyEdges.clear();

        croppedMask = inputFrame.getWindowMask();

        Imgproc.GaussianBlur(croppedMask, croppedMask, new Size(1,1), 0);

//      Manual Threshold
//        Imgproc.Canny(croppedMask, cannyEdgeMask, 50, 100);

        Mat otsu = new Mat();

        // Auto Otsu threshold
        double otsuThreshValue = Imgproc.threshold(croppedMask, otsu, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        Imgproc.Canny(croppedMask, cannyEdgeMask, otsuThreshValue * 0.3, otsuThreshValue);

        Imgproc.findContours(cannyEdgeMask,
                cannyEdges,
                hierarchy,
                Imgproc.RETR_CCOMP,
                Imgproc.CHAIN_APPROX_SIMPLE);

        inputFrame.setCannyEdgeMask(cannyEdgeMask);
        inputFrame.setCannyEdges(cannyEdges);

        return inputFrame;

    }

}
