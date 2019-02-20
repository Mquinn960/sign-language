package mquinn.sign_language.processing;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.IFrame;

public class DownSamplingFrameProcessor implements IFrameProcessor {

    private Mat downSampledInputMat;

    public DownSamplingFrameProcessor() {
        downSampledInputMat = new Mat();
    }

    @Override
    public IFrame process(IFrame inputFrame) {
        return downSample(inputFrame, 2);
    }

    private IFrame downSample(IFrame frameToSample, int samples){
        for (int i=1; i<=samples; i++){
            Imgproc.pyrDown(frameToSample.getRGBA(), downSampledInputMat);
        }
        frameToSample.setDownSampledMat(downSampledInputMat);
        return frameToSample;
    }

    private IFrame downSample(IFrame frameToSample){
        Imgproc.pyrDown(frameToSample.getRGBA(), downSampledInputMat);
        Imgproc.pyrDown(downSampledInputMat, downSampledInputMat);
        frameToSample.setDownSampledMat(downSampledInputMat);

        return frameToSample;
    }

    private IFrame downSample(IFrame frameToSample, double scale) {
        Imgproc.pyrDown(frameToSample.getRGBA(),
                downSampledInputMat,
                new Size((double) frameToSample.getRGBA().width() / scale,
                        (double) frameToSample.getRGBA().height() / scale));

        frameToSample.setDownSampledMat(downSampledInputMat);
        return frameToSample;
    }

}
