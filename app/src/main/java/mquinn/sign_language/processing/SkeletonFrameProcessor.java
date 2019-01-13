package mquinn.sign_language.processing;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class SkeletonFrameProcessor implements IFrameProcessor {

    private Mat inputMask = new Mat();
    private Mat skeletonMask = new Mat();
    private Mat hierarchy = new Mat();
    private List<MatOfPoint> skeletonContours = new ArrayList<>();
    private IThinningStrategy thinningStrategy;

    public SkeletonFrameProcessor(IThinningStrategy inputThinningStrategy) {
        thinningStrategy = inputThinningStrategy;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        skeletonContours.clear();

        inputMask = inputFrame.getMaskedImage();

        skeletonMask = thinningStrategy.thinMask(inputMask);

        Imgproc.findContours(skeletonMask,
                skeletonContours,
                hierarchy,
                Imgproc.RETR_CCOMP,
                Imgproc.CHAIN_APPROX_SIMPLE);

        inputFrame.setSkeleton(skeletonMask);
        inputFrame.setSkeletonContours(skeletonContours);

        return inputFrame;

    }

}
