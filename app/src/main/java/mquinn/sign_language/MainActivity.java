package mquinn.sign_language;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;

import android.app.Activity;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceView;

import mquinn.sign_language.display.ContourDisplayDecorator;
import mquinn.sign_language.display.Displayer;
import mquinn.sign_language.display.FeatureDisplayDecorator;
import mquinn.sign_language.display.HullContourDisplayDecorator;
import mquinn.sign_language.display.IDisplayer;
import mquinn.sign_language.imaging.IFrame;
import mquinn.sign_language.preprocessing.CameraFrameAdapter;
import mquinn.sign_language.preprocessing.IFramePreProcessor;
import mquinn.sign_language.processing.ColourThresholdPointDetector;
import mquinn.sign_language.preprocessing.InputFramePreProcessor;
import mquinn.sign_language.processing.FrameProcessor;
import mquinn.sign_language.processing.IFrameProcessor;

public class MainActivity extends Activity implements CvCameraViewListener2 {

    private CameraBridgeViewBase mOpenCvCameraView;

    private IFramePreProcessor preProcessor;
    private IFrameProcessor processor;
    private IFrame preProcessedFrame, processedFrame;
    private IDisplayer displayer;

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set view parameters
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Set content view
        setContentView(R.layout.color_blob_detection_surface_view);

        // Camera config
        mOpenCvCameraView = findViewById(R.id.color_blob_detection_activity_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        // Generate Frame from input frame
        preProcessedFrame = preProcessor.preProcess(inputFrame);

        // Generate useful information from frame
        processedFrame = processor.process(preProcessedFrame);

        // Display contours/fill
        displayer.setFrame(processedFrame);
        displayer.display();

        // Return processed Mat
        return processedFrame.getRGBA();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        // Create overlay displayer
        displayer = new Displayer();
        //displayer = new ContourDisplayDecorator(displayer);
        //displayer = new HullContourDisplayDecorator(displayer);
        displayer = new FeatureDisplayDecorator(displayer);

        // New up the camera's frame processors
        preProcessor = new InputFramePreProcessor(new CameraFrameAdapter());
        processor = new FrameProcessor(new ColourThresholdPointDetector());
    }

    public void onCameraViewStopped() {

    }

}