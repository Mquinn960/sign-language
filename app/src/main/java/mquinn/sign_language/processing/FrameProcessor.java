package mquinn.sign_language.processing;

import mquinn.sign_language.imaging.IFrame;

public class FrameProcessor implements IFrameProcessor {

    private IFrameProcessor colourThresholdFrameProcessor;
    private IFrameProcessor skeletonFrameProcessor;

    public FrameProcessor(IFrameProcessor inputColourThresholdFrameProcessor, IFrameProcessor inputSkeletonFrameProcessor) {
        colourThresholdFrameProcessor = inputColourThresholdFrameProcessor;
        skeletonFrameProcessor = inputSkeletonFrameProcessor;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        inputFrame = colourThresholdFrameProcessor.process(inputFrame);
        inputFrame = skeletonFrameProcessor.process(inputFrame);

        return inputFrame;

    }

    public IFrameProcessor getColourThresholdFrameProcessor() {
        return colourThresholdFrameProcessor;
    }

    public void setColourThresholdFrameProcessor(IFrameProcessor inputColourThresholdFrameProcessor) {
        this.colourThresholdFrameProcessor = inputColourThresholdFrameProcessor;
    }

    public IFrameProcessor getSkeletonFrameProcessor() {
        return skeletonFrameProcessor;
    }

    public void setSkeletonFrameProcessor(IFrameProcessor inputSkeletonFrameProcessor) {
        this.skeletonFrameProcessor = inputSkeletonFrameProcessor;
    }

}
