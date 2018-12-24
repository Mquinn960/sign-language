package mquinn.sign_language.processing;

import org.opencv.core.Mat;

public class ZhangSuenThinningStrategy implements IThinningStrategy {

    @Override
    public Mat thinMask(Mat inputMat) {
        return inputMat;
    }

    // TODO add thinning implementation

}
