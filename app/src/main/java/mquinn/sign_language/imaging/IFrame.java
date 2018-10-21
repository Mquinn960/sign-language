package mquinn.sign_language.imaging;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import java.util.List;

public interface IFrame {

    Mat getRGBA();
    void setRGBA(Mat inputRGBA);

    List<MatOfPoint> getContours();
    void setCountours(List<MatOfPoint> contours);

}
