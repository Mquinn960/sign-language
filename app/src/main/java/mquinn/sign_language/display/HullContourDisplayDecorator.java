package mquinn.sign_language.display;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class HullContourDisplayDecorator extends DisplayDecorator {

    private Scalar contourColour;
    private Scalar defectColour;
    private Scalar hullPointColour;
    private IFrame frame;

    public HullContourDisplayDecorator(IDisplayer displayer) {
        super(displayer);
        contourColour = new Scalar(255,0,0,255);
        hullPointColour = new Scalar(0,255,0,255);
        defectColour = new Scalar(0,0,255,255);
    }

    @Override
    public void setFrame(IFrame inputFrame) {
        frame = inputFrame;
    }

    @Override
    public void display() {

        // Draw Hull

        Imgproc.drawContours(frame.getRGBA(),
                frame.getHullPoints(),
                -1,
                contourColour,
                2);

        // Draw Points

        for (MatOfPoint pointMat: frame.getHullPoints()){
            for (Point point: pointMat.toList()){
                Imgproc.circle(frame.getRGBA(), point, 5, hullPointColour, 2);
            }
        }

        // Draw Defects

//        List<Integer> defects = frame.getHullDefects();
//
//        Point data[] = contours.get(i).toArray();
//
//        for (int j = 0; j < defects.size(); j = j+4) {
//            Point defect = new Point(1,2);
//
//            Point start = data[cdList.get(j)];
//            Point end = data[cdList.get(j+1)];
//            Point defect = data[cdList.get(j+2)];
//            //Point depth = data[cdList.get(j+3)];
//
//            Imgproc.circle(frame.getRGBA(), defect, 5, defectColour, 2);
//        }
    }

}
