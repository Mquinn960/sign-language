package mquinn.sign_language.processing;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import mquinn.sign_language.imaging.IFrame;

public class ResizingFrameProcessor implements IFrameProcessor {

    private Mat resizedImage = new Mat();
    private Size scaledSize, originalSize;
    private int targetWidth = 100;
    private Size defaultSize = new Size(100, 100);
    double scaleFactor;
    private SizeOperation operation;

    public ResizingFrameProcessor(SizeOperation inputOperation) {
        operation = inputOperation;
    }

    @Override
    public IFrame process(IFrame inputFrame) {

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

        originalSize = inputFrame.getOriginalSize();

        scaledSize = new Size(originalSize.width/scaleFactor, originalSize.height/scaleFactor);

        Imgproc.resize(inputFrame.getRGBA(), resizedImage, defaultSize, 0, 0, Imgproc.INTER_CUBIC);

        inputFrame.setDownSampledMat(resizedImage);
    }

    private void upsize(IFrame inputFrame) {

        Size currentSize = inputFrame.getRGBA().size();

        if (currentSize.height < targetWidth || currentSize.width < targetWidth) {

            Size scaledSize = new Size();

            if (currentSize.height < currentSize.width){
                scaleFactor = Math.max(1, (targetWidth / currentSize.height) + 0.1);
            } else {
                scaleFactor = Math.max(1, (targetWidth / currentSize.width) + 0.1);
            }

            scaledSize.height = Math.ceil(currentSize.height * scaleFactor);
            scaledSize.width = Math.ceil(currentSize.width * scaleFactor);

            Imgproc.resize(inputFrame.getRGBA(), resizedImage, scaledSize, 0, 0, Imgproc.INTER_CUBIC);

            inputFrame.setRGBA(resizedImage);
        }


    }

}
