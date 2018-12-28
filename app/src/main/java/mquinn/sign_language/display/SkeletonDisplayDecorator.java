package mquinn.sign_language.display;

import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.IFrame;

public class SkeletonDisplayDecorator extends DisplayDecorator {

    private Scalar skeletonColour;
    private IFrame frame;

    public SkeletonDisplayDecorator(IDisplayer displayer) {
        super(displayer);
        skeletonColour = new Scalar(0,0,255,255);
    }

    @Override
    public void setFrame(IFrame inputFrame) {
        frame = inputFrame;
    }

    @Override
    public void display() {

        // display skeleton
        Imgproc.drawContours(frame.getRGBA(),
                frame.getSkeletonContours(),
                -1,
                skeletonColour,
                -1);

    }

}
