package mquinn.sign_language.processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;
import mquinn.sign_language.imaging.SkinColourProfile;

public class ColourThresholdFrameProcessor implements IFrameProcessor {

    private static double minContourArea = 0.1;

    private List<MatOfPoint> tempContours = new ArrayList<>();
    private List<MatOfPoint> outerContours = new ArrayList<>();

    private SkinColourProfile skinColourProfile;

    private Mat mHsvMat = new Mat();
    private Mat mMask = new Mat();
    private Mat mDilatedMask = new Mat();
    private Mat mHierarchy = new Mat();
    private Mat mErodedMask = new Mat();
    private Mat mBlurredMask = new Mat();

    public ColourThresholdFrameProcessor(){
        skinColourProfile = SkinColourProfile.getInstance();
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        tempContours.clear();

        // Convert downsampled image to HSV
        Imgproc.cvtColor(inputFrame.getDownSampledMat(), mHsvMat, Imgproc.COLOR_RGB2HSV_FULL);

        // Find out if input Mat is within bounds
        Core.inRange(mHsvMat, skinColourProfile.getLowerBound(), skinColourProfile.getUpperBound(), mMask);

        // Erode
        Imgproc.erode(mMask, mErodedMask, new Mat());

        // Dilating range mask or using gauss blur
        Imgproc.dilate(mErodedMask, mDilatedMask, new Mat());

        // Gauss Blur
        Imgproc.GaussianBlur(mDilatedMask, mBlurredMask, new Size(1,1),0);

        // Get contour
        Imgproc.findContours(mBlurredMask, tempContours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Clear previous outer contours and hull points
        outerContours.clear();

        if (tempContours.size() > 0){
            double maxArea = 0;
            Iterator<MatOfPoint> allContours = tempContours.iterator();
            MatOfPoint maxContour = new MatOfPoint();

            while (allContours.hasNext()) {
                MatOfPoint wrapper = allContours.next();
                double area = Imgproc.contourArea(wrapper);
                if (area > maxArea) {
                    maxArea = area;
                    maxContour = wrapper;
                }
            }

            if (Imgproc.contourArea(maxContour) > minContourArea * maxArea) {
                Core.multiply(maxContour, new Scalar(4, 4), maxContour);
                outerContours.add(maxContour);
            }

            inputFrame.setCountours(outerContours);

        }

        return inputFrame;

    }

}