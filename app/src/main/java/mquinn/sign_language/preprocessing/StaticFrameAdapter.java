package mquinn.sign_language.preprocessing;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;

import mquinn.sign_language.imaging.Frame;
import mquinn.sign_language.imaging.IFrame;

public class StaticFrameAdapter implements IFramePreProcessor {

    @Override
    public IFrame preProcess(CvCameraViewFrame inputFrame) {
        return new Frame(inputFrame.rgba());
    }

}
