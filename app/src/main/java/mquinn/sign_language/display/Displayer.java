package mquinn.sign_language.display;

import mquinn.sign_language.imaging.IFrame;

public class Displayer implements IDisplayer {

    private IFrame frame;

    @Override
    public void setFrame(IFrame inputFrame) {
        frame = inputFrame;
    }

    @Override
    public void display() {

    }

}
