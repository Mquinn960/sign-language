package mquinn.sign_language.processing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.IFrame;

public class InnerContourMaskProcessor implements IFrameProcessor {

    private Mat greyScale =  new Mat();
    private Mat blankMask =  new Mat();
    private Mat croppedMask =  new Mat();
    private Mat initialImage =  new Mat();
    private Scalar contourColour = new Scalar(0, 0, 255);
    private Scalar backgroundColour = new Scalar(255,255,255);

    @Override
    public IFrame process(IFrame inputFrame) {

        // Set initial image
        initialImage = inputFrame.getDownSampledMat();

        // Get greyscale of downsampled input image
        Imgproc.cvtColor(initialImage, greyScale, Imgproc.COLOR_RGBA2GRAY);

        // Create blank background mask
        blankMask = Mat.zeros(greyScale.rows(), greyScale.cols(), CvType.CV_8UC1);

        // Draw contours onto blank mask
        Imgproc.drawContours(blankMask, inputFrame.getContours(), -1, contourColour, -1);

        // Creating the new background
        croppedMask = new Mat(greyScale.rows(),greyScale.cols(),CvType.CV_8UC3);
        croppedMask.setTo(backgroundColour);

        // Copy the inner image to the mask
        //initialImage.copyTo(croppedMask, blankMask);

        // Normalise
        //Core.normalize(blankMask.clone(), blankMask, 0.0, 255.0, Core.NORM_MINMAX, CvType.CV_8UC1);

        // Rescale image
        Core.multiply(croppedMask, new Scalar(4, 4), croppedMask);

        // Set masked image on the input frame
        inputFrame.setMaskedImage(blankMask);

        return inputFrame;

    }
}
