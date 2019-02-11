package mquinn.sign_language.svm;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class PcaReducer {

    public Mat reduce(){

        Mat x = new Mat();
        Mat y = new Mat();

        Core.PCACompute(x , new Mat(), new Mat() , 50);

        Core.PCAProject(x, new Mat(), new Mat(), y);

        return x;
    }

}
