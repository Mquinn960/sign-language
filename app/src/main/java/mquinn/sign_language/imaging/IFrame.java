package mquinn.sign_language.imaging;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

import java.util.List;

public interface IFrame {

    Mat getRGBA();
    void setRGBA(Mat inputRGBA);

    List<MatOfPoint> getContours();
    void setCountours(List<MatOfPoint> contours);

    Mat getDownSampledMat();
    void setDownSampledMat(Mat inputDownSampledMat);

}
