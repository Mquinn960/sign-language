package mquinn.sign_language.processing.postprocessing;

import mquinn.sign_language.imaging.IFrame;

public class OutputFramePostProcessor implements IFramePostProcessor {

    private IFramePostProcessor upScalingFramePostProcessor;

    private IFrame outputFrame;

    public OutputFramePostProcessor(IFramePostProcessor upScalingFramePostProcessor) {

        this.upScalingFramePostProcessor = upScalingFramePostProcessor;

    }

    @Override
    public IFrame postProcess(IFrame inputFrame) {

        outputFrame = upScalingFramePostProcessor.postProcess(inputFrame);

        return outputFrame;

    }

}
