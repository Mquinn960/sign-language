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

    List<MatOfPoint> getFeatures();
    void setFeatures(List<MatOfPoint> features);

    Mat getSkeleton();
    void setSkeleton(Mat skeleton);

    List<MatOfPoint> getCannyEdges();
    void setCannyEdges(List<MatOfPoint> cannyEdges);

    Mat getCannyEdgeMask();
    void setCannyEdgeMask(Mat cannyEdgeMask);

    List<MatOfPoint> getSkeletonContours();
    void setSkeletonContours(List<MatOfPoint> skeletonContours);

    Mat getMaskedImage();
    void setMaskedImage(Mat maskedImage);

    Mat getHierarchy();
    void setHierarchy(Mat hierarchy);

    Mat getWindowMask();
    void setWindowMask(Mat maskedImage);

}
