package mquinn.sign_language.imaging;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import java.util.List;

public class Frame extends Mat implements IFrame {

    private Mat rGBA;
    private List<MatOfPoint> contours;

    public Frame(Mat inputRGBA) {
        rGBA = inputRGBA;
    }

    @Override
    public void setCountours(List<MatOfPoint> inputContours) {
        contours = inputContours;
    }

    @Override
    public List<MatOfPoint> getContours() {
        return contours;
    }

    @Override
    public Mat getRGBA() {
        return rGBA;
    }

    @Override
    public void setRGBA(Mat inputRGBA) {
        rGBA = inputRGBA;
    }
}
