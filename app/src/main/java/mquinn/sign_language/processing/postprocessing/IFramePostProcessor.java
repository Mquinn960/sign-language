package mquinn.sign_language.processing.postprocessing;

import mquinn.sign_language.imaging.IFrame;

public interface IFramePostProcessor {

    IFrame postProcess(IFrame inputFrame);

}
