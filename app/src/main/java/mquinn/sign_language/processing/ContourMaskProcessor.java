package mquinn.sign_language.processing;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.IFrame;

public class ContourMaskProcessor implements IFrameProcessor {

    private Mat greyScale =  new Mat();
    private Mat blankMask =  new Mat();
    private Mat croppedMask =  new Mat();
    private Mat initialImage =  new Mat();

    @Override
    public IFrame process(IFrame inputFrame) {

        croppedMask = new Mat();

        // Set initial image
        initialImage = inputFrame.getDownSampledMat();

        // Get greyscale of downsampled input image
        Imgproc.cvtColor(initialImage, greyScale, Imgproc.COLOR_RGBA2GRAY);

        // Create blank background mask
        blankMask = Mat.zeros(greyScale.rows(), greyScale.cols(), CvType.CV_8UC1);

        // Draw contours onto blank mask
        Imgproc.drawContours(blankMask, inputFrame.getContours(), -1, new Scalar(255,255,255,255), -1);

        // Copy greyscale masked window of the actual hand
        greyScale.copyTo(croppedMask, blankMask);

        // Copy full image details within contoured window
        // Inner hand image with details
        inputFrame.setWindowMask(croppedMask);

        // Set masked image on the input frame
        // Area of hand with no inner details
        inputFrame.setMaskedImage(blankMask);

        return inputFrame;

    }
}
