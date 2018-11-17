package mquinn.sign_language.display;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.IFrame;

public class FeatureDisplayDecorator extends DisplayDecorator {

    private Scalar featureColour;
    private Scalar contourColour;
    private IFrame frame;

    public FeatureDisplayDecorator(IDisplayer displayer) {
        super(displayer);
        contourColour = new Scalar(255,0,0,255);
        featureColour = new Scalar(0,255,0,255);
    }

    @Override
    public void setFrame(IFrame inputFrame) {
        frame = inputFrame;
    }

    @Override
    public void display() {

        Imgproc.drawContours(frame.getRGBA(),
                frame.getContours(),
                -1,
                contourColour,
                -1);

        // Draw Points

        for (MatOfPoint pointMat: frame.getFeatures()){
            for (Point point: pointMat.toList()){
                Imgproc.circle(frame.getRGBA(), point, 5, featureColour, 2);
            }
        }

    }

}
