package mquinn.sign_language.processing;

import mquinn.sign_language.imaging.IFrame;

public class FrameProcessor implements IFrameProcessor {

    private IFrameProcessor colourThresholdFrameProcessor,
                            skeletonFrameProcessor,
                            innerContourMaskProcessor,
                            featureFrameProcessor,
                            cannyEdgeFrameProcessor;

    private IFrame outputFrame;

    public FrameProcessor(IFrameProcessor inputColourThresholdFrameProcessor,
                          IFrameProcessor inputSkeletonFrameProcessor,
                          IFrameProcessor inputInnerContourMaskProcessor,
                          IFrameProcessor inputCannyEdgeFrameProcessor,
                          IFrameProcessor inputFeatureFrameProcessor) {
        colourThresholdFrameProcessor = inputColourThresholdFrameProcessor;
        skeletonFrameProcessor = inputSkeletonFrameProcessor;
        innerContourMaskProcessor = inputInnerContourMaskProcessor;
        cannyEdgeFrameProcessor = inputCannyEdgeFrameProcessor;
        featureFrameProcessor = inputFeatureFrameProcessor;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        outputFrame = colourThresholdFrameProcessor.process(inputFrame);

        outputFrame = innerContourMaskProcessor.process(outputFrame);

        outputFrame = skeletonFrameProcessor.process(outputFrame);

        outputFrame = cannyEdgeFrameProcessor.process(outputFrame);

        outputFrame = featureFrameProcessor.process(outputFrame);

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

    public void setCannyEdgeFrameProcessor(IFrameProcessor inputCannyEdgeFrameProcessor) {
        this.cannyEdgeFrameProcessor = inputCannyEdgeFrameProcessor;
    }

    public void setInnerContourMaskProcessor(IFrameProcessor innerContourMaskProcessor) {
        this.innerContourMaskProcessor = innerContourMaskProcessor;
    }

}
