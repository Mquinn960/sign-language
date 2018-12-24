package mquinn.sign_language.imaging;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import java.util.List;

public class Frame extends Mat implements IFrame {

    private Mat rGBA;
    private List<MatOfPoint> contours;
    private Mat downSampledMat;
    private List<MatOfPoint> features;
    private Mat maskedImage;
    private Mat skeleton;

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

    @Override
    public Mat getDownSampledMat() {
        return downSampledMat;
    }

    @Override
    public void setDownSampledMat(Mat inputDownSampledMat) {
        downSampledMat = inputDownSampledMat;
    }

    @Override
    public List<MatOfPoint> getFeatures() {
        return features;
    }

    @Override
    public void setFeatures(List<MatOfPoint> inputFeatures) {
        features = inputFeatures;
    }

    @Override
    public Mat getSkeleton() {
        return skeleton;
    }

    @Override
    public void setSkeleton(Mat inputSkeleton) {
        skeleton = inputSkeleton;
    }

    @Override
    public Mat getMaskedImage() {
        return maskedImage;
    }

    @Override
    public void setMaskedImage(Mat inputMaskedImage) {
        maskedImage = inputMaskedImage;
    }
}
