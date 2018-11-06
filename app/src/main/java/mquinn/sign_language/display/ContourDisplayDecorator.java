package mquinn.sign_language.display;

import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.IFrame;

public class ContourDisplayDecorator extends DisplayDecorator {

    private Scalar contourColour;
    private IFrame frame;

    public ContourDisplayDecorator(IDisplayer displayer) {
        super(displayer);
        contourColour = new Scalar(0,255,0,255);
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
                             2);

    }

}
