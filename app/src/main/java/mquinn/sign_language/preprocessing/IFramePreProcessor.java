package mquinn.sign_language.preprocessing;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;

import mquinn.sign_language.imaging.Frame;
import mquinn.sign_language.imaging.IFrame;

public interface IFramePreProcessor {

    IFrame preProcess(CvCameraViewFrame inputFrame);

}
