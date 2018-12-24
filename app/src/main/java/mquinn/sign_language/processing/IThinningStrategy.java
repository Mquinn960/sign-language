package mquinn.sign_language.processing;

import org.opencv.core.Mat;

public interface IThinningStrategy {

    Mat thinMask(Mat inputMat);

}
