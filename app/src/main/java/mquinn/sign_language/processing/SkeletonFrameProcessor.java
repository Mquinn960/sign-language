package mquinn.sign_language.processing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.ximgproc.Ximgproc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mquinn.sign_language.imaging.Frame;
import mquinn.sign_language.imaging.IFrame;
import mquinn.sign_language.imaging.SkinColourProfile;

public class SkeletonFrameProcessor implements IFrameProcessor {

    private Mat inputMask = new Mat();
    private Mat skeletonMask = new Mat();
    private Mat hierarchy = new Mat();
    private List<MatOfPoint> skeletonContours = new ArrayList<>();

    private List<MatOfPoint> tempContours = new ArrayList<>();

    private SkinColourProfile skinColourProfile;

    private Mat mHsvMat = new Mat();
    private Mat mMask = new Mat();
    private Mat mDilatedMask = new Mat();
    private Mat mHierarchy = new Mat();
    private Mat mErodedMask = new Mat();
    private Mat mBlurredMask = new Mat();

    private Mat greyScale =  new Mat();
    private Mat blankMask =  new Mat();
    private Mat croppedMask =  new Mat();

    public SkeletonFrameProcessor(){
        skinColourProfile = SkinColourProfile.getInstance();
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        clearContours();
        croppedMask = new Mat();


        // Go through colour thresholding process again

        inputMask = inputFrame.getMaskedImage();

        // Convert downsampled image to HSV
        Imgproc.cvtColor(inputFrame.getDownSampledMat(), mHsvMat, Imgproc.COLOR_RGB2HSV_FULL);

        mHsvMat.copyTo(croppedMask, inputMask);

        // Find out if input Mat is within bounds
        Core.inRange(croppedMask, skinColourProfile.lowerBound, skinColourProfile.upperBound, mMask);

        // Erode
        Imgproc.erode(mMask, mErodedMask, new Mat());

        // Dilating range mask or using gauss blur
        Imgproc.dilate(mErodedMask, mDilatedMask, new Mat());

        // Gauss Blur
        Imgproc.GaussianBlur(mDilatedMask, mBlurredMask, new Size(1,1),0);

        Imgproc.findContours(mBlurredMask, tempContours, mHierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        if (tempContours.size() > 0){
            inputFrame.setCountours(tempContours);
        }


        // Go through contour masking process again

        // Get greyscale of downsampled input image
        Imgproc.cvtColor(inputFrame.getDownSampledMat(), greyScale, Imgproc.COLOR_RGBA2GRAY);

        // Create blank background mask
        blankMask = Mat.zeros(greyScale.rows(), greyScale.cols(), CvType.CV_8UC1);

        // Draw contours onto blank mask
        Imgproc.drawContours(blankMask, inputFrame.getContours(), -1, new Scalar(255,255,255,255), -1);

        // Copy greyscale masked window of the actual hand
        greyScale.copyTo(croppedMask, blankMask);

        // Set masked image on the input frame
        inputFrame.setMaskedImage(blankMask);



        // Actual skeletonisation

        Ximgproc.thinning(blankMask, skeletonMask, Ximgproc.THINNING_ZHANGSUEN);

        Imgproc.findContours(skeletonMask,
                skeletonContours,
                hierarchy,
                Imgproc.RETR_CCOMP,
                Imgproc.CHAIN_APPROX_SIMPLE);

        inputFrame.setSkeleton(skeletonMask);
        inputFrame.setSkeletonContours(skeletonContours);

        return inputFrame;

    }

    private void clearContours(){
        tempContours.clear();
        skeletonContours.clear();
    }

}
