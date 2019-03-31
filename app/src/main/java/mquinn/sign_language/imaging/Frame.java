package mquinn.sign_language.imaging;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;

import java.util.List;

import mquinn.sign_language.svm.LetterClass;

public class Frame extends Mat implements IFrame {

    // Classified letter result of the input image
    private LetterClass letter;

    // Input camera mat RGBA values
    private Mat rGBA;

    // Skin mask found by threshold contouring
    private List<MatOfPoint> contours;

    // Hierarchy of found contours
    private Mat hierarchy;

    // Downsampled starting mat
    private Mat downSampledMat;

    // Features found by feature extraction
    private List<MatOfPoint> features;

    // Area of hand with no inner details
    private Mat maskedImage;

    // Inner hand image with details
    private Mat windowMask;

    // Drawn mat of skeleton contours
    private Mat skeleton;

    // List of skeleton contour vectors
    private List<MatOfPoint> skeletonContours;

    // Drawn mat of canny edges
    private Mat cannyEdgeMask;

    // List of canny edge contour vectors
    private List<MatOfPoint> cannyEdges;

    // Original size of the input frame
    private Size originalSize;

    private Mat hogDesc;

    public Frame(Mat inputRGBA) {
        rGBA = inputRGBA;
    }

    @Override
    public Size getOriginalSize() {
        return originalSize;
    }

    @Override
    public void setOriginalSize(Size inputOriginalSize) {
        originalSize = inputOriginalSize;
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
    public LetterClass getLetterClass() {
        return letter;
    }

    @Override
    public void setLetterClass(LetterClass letter) {
        this.letter = letter;
    }

    @Override
    public Mat getHogDesc() {
        return hogDesc;
    }

    @Override
    public void setHogDesc(Mat hogDesc) {
        this.hogDesc = hogDesc;
    }
}
