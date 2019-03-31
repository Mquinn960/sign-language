package mquinn.sign_language.processing;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class CroppingFrameProcessor implements IFrameProcessor {

    private Mat resizedImage = new Mat();
    private int targetWidth = 100;
    double scaleFactor;

    private Mat testyTest = new Mat();
    private Mat thresh = new Mat();
    private Mat hier = new Mat();
    private List<MatOfPoint> contours = new ArrayList<>();

    private DetectionMethod detectionMethod;

    public CroppingFrameProcessor (DetectionMethod inputDetectionMethod){
        detectionMethod = inputDetectionMethod;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        switch (detectionMethod){
            case CANNY_EDGES:
                inputFrame.setWindowMask(cropImage(inputFrame.getWindowMask()));
                break;
            case SKELETON:
                inputFrame.setMaskedImage(cropImage(inputFrame.getMaskedImage()));
                break;
            case CONTOUR_MASK:
                inputFrame.setWindowMask(cropImage(inputFrame.getWindowMask()));
                break;
            default:
                // do nothing
                break;

        }

        return inputFrame;
    }

    private Mat cropImage(Mat inputMat) {

        thresh.release();
        hier.release();
        contours.clear();
        testyTest.release();

        // Get solo contour
        Imgproc.findContours(inputMat, contours, hier, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        if (contours.size() > 0) {

            MatOfPoint contourSingle = contours.get(0);

            if (Imgproc.contourArea(contourSingle) > 0.1) {

                // Get ROI
                Rect ROI = Imgproc.boundingRect(contourSingle);

                inputMat = new Mat(inputMat, ROI);

                Size currentSize = inputMat.size();

                if (currentSize.height < targetWidth || currentSize.width < targetWidth) {

                    Size scaledSize = new Size();

                    if (currentSize.height < currentSize.width) {
                        scaleFactor = Math.max(1, targetWidth / currentSize.width);
                    } else {
                        scaleFactor = Math.max(1, targetWidth / currentSize.height);
                    }

                    scaledSize.height = Math.floor(currentSize.height * scaleFactor);
                    scaledSize.width = Math.floor(currentSize.width * scaleFactor);

                    resizedImage = new Mat(ROI.size(), inputMat.type());

                    Imgproc.resize(inputMat, resizedImage, scaledSize);

                    return resizedImage;

                }

            }

            return inputMat;

        }

        return inputMat;
    }


}