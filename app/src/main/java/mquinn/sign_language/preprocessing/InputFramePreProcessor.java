package mquinn.sign_language.preprocessing;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.core.Mat;

import mquinn.sign_language.imaging.Frame;

public class InputFramePreProcessor implements IFramePreProcessor {

    private IFramePreProcessor frameAdapter;

    public InputFramePreProcessor(IFramePreProcessor inputFrameAdapter) {
        frameAdapter = inputFrameAdapter;
    }

    @Override
    public Frame preProcess(CvCameraViewFrame inputFrame) {
        return frameAdapter.preProcess(inputFrame);
    }

    public IFramePreProcessor getFrameAdapter() {
        return frameAdapter;
    }

    public void setFrameAdapter(IFramePreProcessor frameAdapter) {
        this.frameAdapter = frameAdapter;
    }

}
