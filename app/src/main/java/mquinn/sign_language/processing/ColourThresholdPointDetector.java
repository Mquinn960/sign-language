package mquinn.sign_language.processing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
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

    private List<MatOfPoint> tempContours = new ArrayList<MatOfPoint>();
    private List<MatOfPoint> outerContours = new ArrayList<MatOfPoint>();

    private List<MatOfPoint> hullPoints = new ArrayList<MatOfPoint>();
    private List<Integer> hullDefects = new ArrayList<Integer>();

    private MatOfInt4 hullDefectsTemp = new MatOfInt4();

    private MatOfPoint maxCountour = new MatOfPoint();
    private MatOfInt hull = new MatOfInt();
    private MatOfPoint mopOut = new MatOfPoint();

    private IFrame frame = new Frame(new Mat());

    private Mat mPyrDownMat = new Mat();
    private Mat mHsvMat = new Mat();
    private Mat mMask = new Mat();
    private Mat mDilatedMask = new Mat();
    private Mat mHierarchy = new Mat();
    private Mat mErodedMask = new Mat();
    private Mat mBlurredMask = new Mat();

    @Override
    public void process() {

        // H
        mLowerBound.val[0] = 0;
        mUpperBound.val[0] = 25;

        // S
        mLowerBound.val[1] = 40;
        mUpperBound.val[1] = 255;

        // V
        mLowerBound.val[2] = 60;
        mUpperBound.val[2] = 255;

        // A
        mLowerBound.val[3] = 0;
        mUpperBound.val[3] = 255;

        tempContours.clear();
        downSample(frame);

        // Convert downsampled image to HSV
        Imgproc.cvtColor(mPyrDownMat, mHsvMat, Imgproc.COLOR_RGB2HSV_FULL);

        // Find out if input Mat is within bounds
        Core.inRange(mHsvMat, mLowerBound, mUpperBound, mMask);

        // Erode
        Imgproc.erode(mMask, mErodedMask, new Mat());

        // Dilating range mask or using gauss blur
        Imgproc.dilate(mErodedMask, mDilatedMask, new Mat());

        // Gauss Blur
        Imgproc.GaussianBlur(mDilatedMask, mBlurredMask, new Size(1,1),0);

        Imgproc.findContours(mBlurredMask, tempContours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        outerContours.clear();
        hullPoints.clear();
        //hullDefects.clear();

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

        if (Imgproc.contourArea(maxContour) > mMinContourArea * maxArea) {
            Core.multiply(maxContour, new Scalar(4, 4), maxContour);
            outerContours.add(maxContour);
        }

        Iterator<MatOfPoint> contourIterator = outerContours.iterator();
        while (contourIterator.hasNext()) {
            maxCountour = contourIterator.next();
            Imgproc.convexHull(maxCountour, hull, false);
        }

        mopOut.create((int) hull.size().height, 1, CvType.CV_32SC2);

        for (int i = 0; i < hull.size().height; i++) {
            int index = (int) hull.get(i, 0)[0];
            double[] point = new double[]{
                    maxCountour.get(index, 0)[0], maxCountour.get(index, 0)[1]
            };
            mopOut.put(i, 0, point);
        }

        if (Imgproc.isContourConvex(mopOut)) {
            hullPoints.add(mopOut);
            Imgproc.convexityDefects(maxCountour, hull, hullDefectsTemp);
            hullDefects = hullDefectsTemp.toList();
        }

    }

    @Override
    public List<MatOfPoint> getContours() {
        return outerContours;
    }

    @Override
    public List<MatOfPoint> getHullPoints() {
        return hullPoints;
    }

    @Override
    public List<Integer> getHullDefects() {
        return hullDefects;
    }

    public IFrame getFrame() {
        return frame;
    }

    @Override
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