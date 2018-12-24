package mquinn.sign_language.processing.preprocessing;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;

import mquinn.sign_language.imaging.Frame;
import mquinn.sign_language.imaging.IFrame;
import mquinn.sign_language.processing.IFrameProcessor;

public class CameraFrameAdapter implements IFramePreProcessor {

    private IFrameProcessor downSampler;
    private IFrame outputFrame;

    public CameraFrameAdapter(IFrameProcessor downSamplerFrameProcessor) {
        downSampler = downSamplerFrameProcessor;
    }

    @Override
    public IFrame preProcess(CvCameraViewFrame inputFrame) {

        outputFrame = new Frame(inputFrame.rgba());
        outputFrame = downSampler.process(outputFrame);

        return outputFrame;
    }

}
