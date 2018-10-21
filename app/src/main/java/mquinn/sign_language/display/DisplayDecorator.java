package mquinn.sign_language.display;

public abstract class DisplayDecorator implements IDisplayer {

    protected IDisplayer displayer;

    protected DisplayDecorator(IDisplayer displayer) {
        this.displayer = displayer;
    }

    @Override
    public void display() {
        displayer.display();
    }
}
