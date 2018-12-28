package mquinn.sign_language.processing;

import org.opencv.core.Mat;
import org.opencv.ximgproc.Ximgproc;

public class ZhangSuenThinningStrategy implements IThinningStrategy {

    private Mat outputMat = new Mat();

    @Override
    public Mat thinMask(Mat inputMat) {

        Ximgproc.thinning(inputMat, outputMat, Ximgproc.THINNING_ZHANGSUEN);

        return outputMat;
    }

}
