package mquinn.sign_language.processing;

import mquinn.sign_language.imaging.IFrame;

public class FrameProcessor implements IFrameProcessor {

    private IFrameProcessor colourThresholdFrameProcessor;
    private IFrameProcessor skeletonFrameProcessor;
    private IFrameProcessor innerContourMaskProcessor;
    private IFrameProcessor featureFrameProcessor;

    private IFrame outputFrame;

    public FrameProcessor(IFrameProcessor inputColourThresholdFrameProcessor,
                          IFrameProcessor inputSkeletonFrameProcessor,
                          IFrameProcessor inputInnerContourMaskProcessor,
                          IFrameProcessor inputFeatureFrameProcessor) {
        colourThresholdFrameProcessor = inputColourThresholdFrameProcessor;
        skeletonFrameProcessor = inputSkeletonFrameProcessor;
        innerContourMaskProcessor = inputInnerContourMaskProcessor;
        featureFrameProcessor = inputFeatureFrameProcessor;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        outputFrame = colourThresholdFrameProcessor.process(inputFrame);

        outputFrame = innerContourMaskProcessor.process(outputFrame);

        outputFrame = featureFrameProcessor.process(outputFrame);

        outputFrame = skeletonFrameProcessor.process(outputFrame);

        return outputFrame;

    }

    public void setColourThresholdFrameProcessor(IFrameProcessor inputColourThresholdFrameProcessor) {
        this.colourThresholdFrameProcessor = inputColourThresholdFrameProcessor;
    }

    public void setSkeletonFrameProcessor(IFrameProcessor inputSkeletonFrameProcessor) {
        this.skeletonFrameProcessor = inputSkeletonFrameProcessor;
    }

    public void setFeatureFrameProcessor(IFrameProcessor inputFeatureFrameProcessor) {
        this.featureFrameProcessor = inputFeatureFrameProcessor;
    }

    public void setInnerContourMaskProcessor(IFrameProcessor innerContourMaskProcessor) {
        this.innerContourMaskProcessor = innerContourMaskProcessor;
    }

}
