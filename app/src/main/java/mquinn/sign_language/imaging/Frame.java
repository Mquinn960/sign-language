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
    private Mat windowMask;
    private Mat skeleton;
    private List<MatOfPoint> skeletonContours;
    private Mat cannyEdgeMask;
    private List<MatOfPoint> cannyEdges;
    private Mat hierarchy;

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
    public List<MatOfPoint> getCannyEdges() {
        return cannyEdges;
    }

    @Override
    public void setCannyEdges(List<MatOfPoint> inputCannyEdges) {
        cannyEdges = inputCannyEdges;
    }

    @Override
    public Mat getCannyEdgeMask() {
        return cannyEdgeMask;
    }

    @Override
    public void setCannyEdgeMask(Mat inputCannyEdgeMask) {
        cannyEdgeMask = inputCannyEdgeMask;
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
    public List<MatOfPoint> getSkeletonContours() {
        return skeletonContours;
    }

    @Override
    public void setSkeletonContours(List<MatOfPoint> inputSkeletonContours) {
        skeletonContours = inputSkeletonContours;
    }

    @Override
    public Mat getMaskedImage() {
        return maskedImage;
    }

    @Override
    public Mat getWindowMask() {
        return windowMask;
    }

    @Override
    public void setWindowMask(Mat inputWindowMask) {
        windowMask = inputWindowMask;
    }

    @Override
    public void setMaskedImage(Mat inputMaskedImage) {
        maskedImage = inputMaskedImage;
    }

    @Override
    public Mat getHierarchy() {
        return hierarchy;
    }

    @Override
    public void setHierarchy(Mat hierarchy) {
        this.hierarchy = hierarchy;
    }
}
