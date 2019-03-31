package mquinn.sign_language.imaging;

import org.opencv.core.Scalar;

public class SkinColourProfile {

    private static SkinColourProfile instance = null;

    public Scalar lowerBound = new Scalar(0);
    public Scalar upperBound = new Scalar(0);

    protected SkinColourProfile() {
        setDefaultSkinColourProfile();
    }

    public static SkinColourProfile getInstance() {
        if (instance == null){
            instance = new SkinColourProfile();
        }
        return instance;
    }

    public void setHSVAValue(Character property, int lower, int upper){

        switch (property){
            case 'H':
                lowerBound.val[HSVA.H.getScalarPosition()] = lower;
                upperBound.val[HSVA.H.getScalarPosition()] = upper;
            case 'S':
                lowerBound.val[HSVA.S.getScalarPosition()] = lower;
                upperBound.val[HSVA.S.getScalarPosition()] = upper;
            case 'V':
                lowerBound.val[HSVA.V.getScalarPosition()] = lower;
                upperBound.val[HSVA.V.getScalarPosition()] = upper;
            case 'A':
                lowerBound.val[HSVA.A.getScalarPosition()] = lower;
                upperBound.val[HSVA.A.getScalarPosition()] = upper;
        }

    }

    public void setLowerBound(Scalar lowerBound) {
        this.lowerBound = lowerBound;
    }

    public void setUpperBound(Scalar upperBound) {
        this.upperBound = upperBound;
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

}
