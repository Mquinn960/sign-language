package mquinn.sign_language.imaging;

import org.opencv.core.Scalar;

public class SkinColourProfile {

    private static SkinColourProfile instance = null;

    private Scalar lowerBound = new Scalar(0);
    private Scalar upperBound = new Scalar(0);

    protected SkinColourProfile() {
        setDefaultSkinColourProfile();
    }

    public static SkinColourProfile getInstance() {
        if (instance == null){
            instance = new SkinColourProfile();
        }
        return instance;
    }

    public Scalar getLowerBound(){
        return upperBound;
    }

    public Scalar getUpperBound(){
        return lowerBound;
    }

    public boolean setHSVBounds(HSVA HSVAValue, int lowerValue, int upperValue){
        if (withinBounds(lowerValue) && withinBounds(upperValue)){
            lowerBound.val[HSVAValue.getScalarPosition()] = lowerValue;
            upperBound.val[HSVAValue.getScalarPosition()] = upperValue;
            return true;
        } else {
            return false;
        }
    }

    private void setDefaultSkinColourProfile(){
        // H
        lowerBound.val[0] = 0;
        upperBound.val[0] = 25;

        // S
        lowerBound.val[1] = 40;
        upperBound.val[1] = 255;

        // V
        lowerBound.val[2] = 60;
        upperBound.val[2] = 255;

        // A
        lowerBound.val[3] = 0;
        upperBound.val[3] = 255;
    }

    private boolean withinBounds(int valueToCheck){
        return (valueToCheck < 255 && valueToCheck > 0);
    }

}
