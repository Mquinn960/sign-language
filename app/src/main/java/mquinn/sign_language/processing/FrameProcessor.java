package mquinn.sign_language.processing;

import mquinn.sign_language.imaging.IFrame;

public class FrameProcessor implements IFrameProcessor {

    private IFrameProcessor colourThresholdFrameProcessor;

    public FrameProcessor(IFrameProcessor inputColourThresholdFrameProcessor) {
        colourThresholdFrameProcessor = inputColourThresholdFrameProcessor;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        inputFrame = colourThresholdFrameProcessor.process(inputFrame);

        return inputFrame;

    }

    public IFrameProcessor getColourThresholdFrameProcessor() {
        return colourThresholdFrameProcessor;
    }

    public void setColourThresholdFrameProcessor(IFrameProcessor inputColourThresholdFrameProcessor) {
        this.colourThresholdFrameProcessor = inputColourThresholdFrameProcessor;
    }

}
