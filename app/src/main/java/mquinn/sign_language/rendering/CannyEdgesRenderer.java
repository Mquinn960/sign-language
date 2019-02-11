package mquinn.sign_language.rendering;

import org.opencv.core.Core;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.IFrame;

public class CannyEdgesRenderer implements IRenderer {

    private Scalar edgesColour;

    public CannyEdgesRenderer() {
        setDefaultColour();
    }

    @Override
    public void display(IFrame inputFrame) {

        // display edges
        Imgproc.drawContours(inputFrame.getRGBA(),
                inputFrame.getCannyEdges(),
                -1,
                edgesColour,
                1);

    }

    private void setDefaultColour(){
        edgesColour = new Scalar(0,0,255,255);
    }

}
