package mquinn.sign_language.preprocessing;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;

import mquinn.sign_language.imaging.Frame;

public class StaticFrameAdapter implements IFramePreProcessor {

    @Override
    public Frame preProcess(CvCameraViewFrame inputFrame) {
        return new Frame(inputFrame.rgba());
    }

}
