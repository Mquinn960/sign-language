package mquinn.sign_language.processing;

import mquinn.sign_language.imaging.Frame;
import mquinn.sign_language.imaging.IFrame;

public interface IFrameProcessor {

    Frame process(IFrame inputFrame);

}
