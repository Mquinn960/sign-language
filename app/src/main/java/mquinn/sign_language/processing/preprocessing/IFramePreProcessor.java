package mquinn.sign_language.processing.preprocessing;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;

import mquinn.sign_language.imaging.IFrame;

public interface IFramePreProcessor {

    IFrame preProcess(CvCameraViewFrame inputFrame);

}
