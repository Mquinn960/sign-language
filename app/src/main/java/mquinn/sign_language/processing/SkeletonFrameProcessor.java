package mquinn.sign_language.processing;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;
import org.opencv.ximgproc.Ximgproc;

import java.util.ArrayList;
import java.util.List;

import mquinn.sign_language.imaging.IFrame;

public class SkeletonFrameProcessor implements IFrameProcessor {

    private Mat skeletonMask = new Mat();
    private Mat hierarchy = new Mat();
    private List<MatOfPoint> skeletonContours = new ArrayList<>();

    @Override
    public IFrame process(IFrame inputFrame) {

        skeletonContours.clear();

        Ximgproc.thinning(inputFrame.getMaskedImage(), skeletonMask, Ximgproc.THINNING_ZHANGSUEN);

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
