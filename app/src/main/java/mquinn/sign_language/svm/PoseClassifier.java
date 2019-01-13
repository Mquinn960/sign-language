package mquinn.sign_language.svm;

import org.opencv.core.Mat;
import org.opencv.ml.SVM;

public class PoseClassifier {

    private SVM svm = SVM.create();

    void doSomething(){



//    Mat responses = new Mat(1, sizeOfDataset, CvType.CV_32F);
//    responses.put(0, 0, labelArray); // labelArray is a float[] of labels for the data
//
//    CvSVM svm = new CvSVM();
//    CvSVMParams params = new CvSVMParams();
//    params.set_svm_type(CvSVM.C_SVC);
//    params.set_kernel_type(CvSVM.LINEAR);
//    params.set_term_crit(new TermCriteria(TermCriteria.EPS, 100, 1e-6)); // use TermCriteria.COUNT for speed
//
//    svm.train_auto(trainData, responses, new Mat(), new Mat(), params);




    }


//    svm.setKernel(SVM.LINEAR);
//        svm.setType(SVM.C_SVC);
//    // errors here
//        svm.train(TRAINING_DATA, Ml.ROW_SAMPLE, TRAINING_LABELS);
//
//    Mat RESULTS = new Mat();
//    // do i need to predict test features one-by-one?
//    // what is flags?
//        svm.predict(TESTING_DATA, RESULTS, flags);

//    Mat responses = new Mat(1, sizeOfDataset, CvType.CV_32F);
//    responses.put(0, 0, labelArray); // labelArray is a float[] of labels for the data
//
//    CvSVM svm = new CvSVM();
//    CvSVMParams params = new CvSVMParams();
//    params.set_svm_type(CvSVM.C_SVC);
//    params.set_kernel_type(CvSVM.LINEAR);
//    params.set_term_crit(new TermCriteria(TermCriteria.EPS, 100, 1e-6)); // use TermCriteria.COUNT for speed
//
//    svm.train_auto(trainData, responses, new Mat(), new Mat(), params);

}
