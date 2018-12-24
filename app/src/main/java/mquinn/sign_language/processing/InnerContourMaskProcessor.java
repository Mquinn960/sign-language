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
        Imgproc.drawContours(blankMask, inputFrame.getContours(), -1, new Scalar(255), -1);

        // Set masked image on the input frame
        inputFrame.setMaskedImage(blankMask);

        return inputFrame;

    }
}
