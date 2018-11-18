package mquinn.sign_language.processing;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.IFrame;

public class DownSamplingFrameProcessor implements IFrameProcessor {

    private Mat downSampledInputMat = new Mat();

    @Override
    public IFrame process(IFrame inputFrame) {
        return downSample(inputFrame);
    }

    private IFrame downSample(IFrame inputFrame){
        Imgproc.pyrDown(inputFrame.getRGBA(), downSampledInputMat);
        Imgproc.pyrDown(downSampledInputMat, downSampledInputMat);
        inputFrame.setDownSampledMat(downSampledInputMat);
        return inputFrame;
    }

    private IFrame downSample(IFrame inputFrame, double scale) {
        Imgproc.pyrDown(inputFrame.getRGBA(),
                downSampledInputMat,
                new Size((double) inputFrame.getRGBA().width() / scale,
                        (double) inputFrame.getRGBA().height() / scale));

        inputFrame.setDownSampledMat(downSampledInputMat);
        return inputFrame;
    }

}
