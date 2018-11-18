package mquinn.sign_language.processing;

import mquinn.sign_language.imaging.IFrame;

public class FrameProcessor implements IFrameProcessor {

    private IFrameProcessor colourThresholdFrameProcessor;
    private IFrameProcessor skeletonFrameProcessor;
    private IFrameProcessor innerContourMaskProcessor;

    public FrameProcessor(IFrameProcessor inputColourThresholdFrameProcessor,
                          IFrameProcessor inputSkeletonFrameProcessor,
                          IFrameProcessor inputInnerContourMaskProcessor) {
        colourThresholdFrameProcessor = inputColourThresholdFrameProcessor;
        skeletonFrameProcessor = inputSkeletonFrameProcessor;
        innerContourMaskProcessor = inputInnerContourMaskProcessor;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        inputFrame = colourThresholdFrameProcessor.process(inputFrame);

        inputFrame = innerContourMaskProcessor.process(inputFrame);

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
