package mquinn.sign_language.processing;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.IFrame;

public class ResizingFrameProcessor implements IFrameProcessor {

    private Mat resizedImage = new Mat();
    private Size scaledSize, originalSize;
    private int targetWidth = 256;
    private Size defaultSize = new Size(240, 240);
    int scaleFactor;
    private SizeOperation operation;

    public ResizingFrameProcessor(SizeOperation inputOperation) {
        operation = inputOperation;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

        originalSize = inputFrame.getOriginalSize();

        scaleFactor = Math.max(1, (int) Math.floor(originalSize.width / targetWidth));

        switch(operation){
            case UP:
                upsize(inputFrame);
                break;
            case DOWN:
                downsize(inputFrame);
                break;
            default:
                break;
        }

        return inputFrame;

    }

    private void downsize(IFrame inputFrame){

        scaledSize = new Size(originalSize.width/scaleFactor, originalSize.height/scaleFactor);

        Imgproc.resize(inputFrame.getRGBA(), resizedImage, defaultSize, 0, 0, Imgproc.INTER_CUBIC);
//        Imgproc.resize(inputFrame.getRGBA(), resizedImage, scaledSize, 0, 0, Imgproc.INTER_CUBIC);

        inputFrame.setDownSampledMat(resizedImage);
    }

    private void upsize(IFrame inputFrame) {

        Imgproc.resize(inputFrame.getRGBA(), resizedImage, originalSize, 0, 0, Imgproc.INTER_CUBIC);

        inputFrame.setRGBA(resizedImage);
    }

}
