package mquinn.sign_language.imaging;

public enum HSVA {

    H (0),
    S (1),
    V (2),
    A (3);

    private final int scalarPosition;

    private HSVA(int inputScalarPosition){
        this.scalarPosition = inputScalarPosition;
    }

    public int getScalarPosition(){
        return scalarPosition;
    }

}
