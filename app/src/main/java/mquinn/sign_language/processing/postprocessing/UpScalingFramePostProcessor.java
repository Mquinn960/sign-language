package mquinn.sign_language.processing.postprocessing;

import org.opencv.core.Core;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import java.util.Iterator;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class UpScalingFramePostProcessor implements IFramePostProcessor {

    private Scalar scalingFactor = new Scalar(4,4);

    public UpScalingFramePostProcessor() {
    }

    @Override
    public IFrame postProcess(IFrame inputFrame) {

        calculateScalingFactor(inputFrame);
        return upScale(inputFrame);

    }

    private IFrame upScale(IFrame inputFrame){

        iterativeUpScale(inputFrame.getContours());
        iterativeUpScale(inputFrame.getFeatures());
        iterativeUpScale(inputFrame.getSkeletonContours());
        iterativeUpScale(inputFrame.getCannyEdges());

        return inputFrame;
    }

    private void iterativeUpScale(List<MatOfPoint> pointsToUpScale){
        if (pointsToUpScale != null){
            if (pointsToUpScale.size() > 0){
                Iterator<MatOfPoint> allMatOfPoint = pointsToUpScale.iterator();

                while (allMatOfPoint.hasNext()) {
                    upScaleMatOfPoint(allMatOfPoint.next());
                }

            }
        }
    }

    private void upScaleMatOfPoint(MatOfPoint inputMatOfPoint){
        Core.multiply(inputMatOfPoint, scalingFactor, inputMatOfPoint);
    }

    private void calculateScalingFactor(IFrame inputFrame){

        Size originalSize = inputFrame.getOriginalSize();
        Size currentSize = inputFrame.getDownSampledMat().size();

        double originalWidth = originalSize.width;
        double currentWidth = currentSize.width;

        double scalingRatio = originalWidth / currentWidth;

        scalingFactor = new Scalar(scalingRatio, scalingRatio);

    }

}
