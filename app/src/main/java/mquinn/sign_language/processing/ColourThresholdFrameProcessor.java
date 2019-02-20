package mquinn.sign_language.processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mquinn.sign_language.imaging.IFrame;
import mquinn.sign_language.imaging.SkinColourProfile;

public class ColourThresholdFrameProcessor implements IFrameProcessor {

    private static double minContourArea = 0.1;
    private static double minChildContourAreaMod = 0.025;

    private List<MatOfPoint> tempContours = new ArrayList<>();
    private List<MatOfPoint> outerContours = new ArrayList<>();

    private SkinColourProfile skinColourProfile;
    private DetectionMethod detectionMethod;

    private Mat mHsvMat = new Mat();
    private Mat mMask = new Mat();
    private Mat mDilatedMask = new Mat();
    private Mat mHierarchy = new Mat();
    private Mat mErodedMask = new Mat();
    private Mat mBlurredMask = new Mat();

    public ColourThresholdFrameProcessor(DetectionMethod inputDetectionMethod){
        skinColourProfile = SkinColourProfile.getInstance();
        detectionMethod = inputDetectionMethod;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        clearContours();

        // Convert downsampled image to HSV
        Imgproc.cvtColor(inputFrame.getDownSampledMat(), mHsvMat, Imgproc.COLOR_RGB2HSV_FULL);

        // Find out if input Mat is within bounds
        Core.inRange(mHsvMat, skinColourProfile.lowerBound, skinColourProfile.upperBound, mMask);

        // Erode
        Imgproc.erode(mMask, mErodedMask, new Mat());

        // Dilating range mask or using gauss blur
        Imgproc.dilate(mErodedMask, mDilatedMask, new Mat());

        // Gauss Blur
        Imgproc.GaussianBlur(mDilatedMask, mBlurredMask, new Size(1,1),0);

        Imgproc.findContours(mBlurredMask, tempContours, mHierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        int maxContourIdx = 0;

        if (tempContours.size() > 0) {
            double maxArea = 0;
            MatOfPoint maxContour = new MatOfPoint();
            for (int i = 0; i < tempContours.size(); i++) {
                MatOfPoint wrapper = tempContours.get(i);
                double area = Imgproc.contourArea(tempContours.get(i));
                if (area > maxArea) {
                    maxArea = area;
                    maxContour = wrapper;
                    maxContourIdx = i;
                }
            }

            // Add the largest contour to the contours array
            if (Imgproc.contourArea(maxContour) > minContourArea * maxArea) {
                outerContours.add(maxContour);
            }

            // Add child contours of the largest contour
            for (int i = 0; i < mHierarchy.cols(); i++){
                if (mHierarchy.get(0,i)[3] == maxContourIdx){
                    if (Imgproc.contourArea(tempContours.get(i)) > minChildContourAreaMod * maxArea){
                        outerContours.add(tempContours.get(i));
                    }
                }
            }

            inputFrame.setCountours(outerContours);
        }

        return inputFrame;

    }

    private void clearContours(){
        tempContours.clear();
        outerContours.clear();
    }

}