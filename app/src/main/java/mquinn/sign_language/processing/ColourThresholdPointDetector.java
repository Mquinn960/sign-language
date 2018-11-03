package mquinn.sign_language.processing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.Frame;
import mquinn.sign_language.imaging.IFrame;

public class ColourThresholdPointDetector implements IPointDetector {

    // Lower and Upper bounds for range checking in HSV color space
    private Scalar mLowerBound = new Scalar(0);
    private Scalar mUpperBound = new Scalar(0);

    // Minimum contour area in percent for contours filtering
    private static double mMinContourArea = 0.1;

    // Color radius for range checking in HSV color space
    private Scalar mColorRadius = new Scalar(25,50,50,0);

    private Scalar mBlobColorHsv = new Scalar(30,41,46,0);

    private List<MatOfPoint> tempContours = new ArrayList<MatOfPoint>();
    private List<MatOfPoint> outerContours = new ArrayList<MatOfPoint>();
    private Mat mSpectrum = new Mat();

    private IFrame frame = new Frame(new Mat());

    private Mat mPyrDownMat = new Mat();
    private Mat mHsvMat = new Mat();
    private Mat mMask = new Mat();
    private Mat mDilatedMask = new Mat();
    private Mat mHierarchy = new Mat();
    private Mat mErodedMask = new Mat();
    private Mat mBlurredMask = new Mat();

    public ColourThresholdPointDetector() {
        setHsvColor(mBlobColorHsv);
    }

    private void setHsvColor(Scalar hsvColor) {
        double minH = (hsvColor.val[0] >= mColorRadius.val[0]) ? hsvColor.val[0] - mColorRadius.val[0] : 0;
        double maxH = (hsvColor.val[0]+mColorRadius.val[0] <= 255) ? hsvColor.val[0] + mColorRadius.val[0] : 255;

        mLowerBound.val[0] = 0;
        mUpperBound.val[0] = 20;

        mLowerBound.val[1] = 48;
        mUpperBound.val[1] = 255;

        mLowerBound.val[2] = 80;
        mUpperBound.val[2] = 255;

        mLowerBound.val[3] = 0;
        mUpperBound.val[3] = 255;

    }

    public void process() {

        tempContours.clear();
        downSample(frame);

        // Convert downsampled image to HSV
        Imgproc.cvtColor(mPyrDownMat, mHsvMat, Imgproc.COLOR_RGB2HSV_FULL);

        // Find out if input Mat is within bounds
        Core.inRange(mHsvMat, mLowerBound, mUpperBound, mMask);

        // Erode
        Imgproc.dilate(mMask, mErodedMask, new Mat());

        // Dilating range mask or using gauss blur
        Imgproc.dilate(mErodedMask, mDilatedMask, new Mat());

        // Gauss Blur
        //Imgproc.GaussianBlur(mDilatedMask, mBlurredMask, new Size(3,3),0);

        Imgproc.findContours(mDilatedMask, tempContours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Invert Mask
        //bitwise_not(dst, dst);

        // Get maximal contour
        double maxArea = 0;
        Iterator<MatOfPoint> allContours = tempContours.iterator();
        while (allContours.hasNext()) {
            MatOfPoint wrapper = allContours.next();
            double area = Imgproc.contourArea(wrapper);
            if (area > maxArea) {
                maxArea = area;
            }
        }

        // Filter contours by area and resize to fit the original image size
        outerContours.clear();
        allContours = tempContours.iterator();
        while (allContours.hasNext()) {
            MatOfPoint contour = allContours.next();
            if (Imgproc.contourArea(contour) > mMinContourArea*maxArea) {
                Core.multiply(contour, new Scalar(4,4), contour);
                outerContours.add(contour);
            }
        }

    }

    public List<MatOfPoint> getPoints() {
        return outerContours;
    }

    public IFrame getFrame() {
        return frame;
    }

    public void setFrame(IFrame inputFrame) {
        frame = inputFrame;
    }

    private void downSample(IFrame inputFrame){
        Imgproc.pyrDown(inputFrame.getRGBA(), mPyrDownMat);
        Imgproc.pyrDown(mPyrDownMat, mPyrDownMat);
    }

    private void downSample(IFrame inputFrame, double scale) {
        Imgproc.pyrDown(inputFrame.getRGBA(),
                        inputFrame.getRGBA(),
                        new Size((double) inputFrame.getRGBA().width() / scale,
                (double) inputFrame.getRGBA().height() / scale));
    }

}