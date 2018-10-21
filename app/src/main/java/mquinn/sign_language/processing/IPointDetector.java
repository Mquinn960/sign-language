package mquinn.sign_language.processing;

import org.opencv.core.MatOfPoint;

import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public interface IPointDetector {

    void process();

    void setFrame(IFrame inputFrame);
    List<MatOfPoint> getPoints();

}
