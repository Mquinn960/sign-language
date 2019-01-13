package mquinn.sign_language.display;

import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.IFrame;

public class CannyEdgesDisplayDecorator extends DisplayDecorator {

    private Scalar edgesColour;
    private IFrame frame;

    public CannyEdgesDisplayDecorator(IDisplayer displayer) {
        super(displayer);
        edgesColour = new Scalar(0,0,255,255);
    }

    @Override
    public void setFrame(IFrame inputFrame) {
        frame = inputFrame;
    }

    @Override
    public void display() {

        // display edges
        Imgproc.drawContours(frame.getRGBA(),
                frame.getCannyEdges(),
                -1,
                edgesColour,
                -1);

    }

}
