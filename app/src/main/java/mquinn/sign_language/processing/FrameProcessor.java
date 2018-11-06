package mquinn.sign_language.processing;

import mquinn.sign_language.imaging.Frame;
import mquinn.sign_language.imaging.IFrame;

public class FrameProcessor implements IFrameProcessor {

    private IPointDetector pointDetector;

    public FrameProcessor(IPointDetector inputPointDetector) {
        pointDetector = inputPointDetector;
    }

    @Override
    public Frame process(IFrame inputFrame) {

        pointDetector.setFrame(inputFrame);
        pointDetector.process();
        inputFrame.setCountours(pointDetector.getContours());
        inputFrame.setHullPoints(pointDetector.getHullPoints());
        inputFrame.setHullDefects(pointDetector.getHullDefects());

        return (Frame) inputFrame;

    }

    public IPointDetector getPointDetector() {
        return pointDetector;
    }

    public void setPointDetector(IPointDetector pointDetector) {
        this.pointDetector = pointDetector;
    }
}
