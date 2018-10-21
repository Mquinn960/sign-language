package mquinn.sign_language.preprocessing;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;

import mquinn.sign_language.imaging.Frame;

public interface IFramePreProcessor {

    Frame preProcess(CvCameraViewFrame inputFrame);

}
