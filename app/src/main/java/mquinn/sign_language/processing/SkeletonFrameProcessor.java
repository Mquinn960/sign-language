package mquinn.sign_language.processing;

import org.opencv.core.Mat;

import mquinn.sign_language.imaging.IFrame;

public class SkeletonFrameProcessor implements IFrameProcessor {

    private Mat inputMask = new Mat();
    private Mat skeletonMask = new Mat();
    private IThinningStrategy thinningStrategy;

    public SkeletonFrameProcessor(IThinningStrategy inputThinningStrategy) {
        thinningStrategy = inputThinningStrategy;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        inputMask = inputFrame.getMaskedImage();

        skeletonMask = thinningStrategy.thinMask(inputMask);

        // Set masked image on the input frame
        inputFrame.setSkeleton(skeletonMask);

        return inputFrame;

    }

}
