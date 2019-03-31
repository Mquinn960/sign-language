package mquinn.sign_language.svm;

public enum LetterClass {

    NONE(0),
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9),
    J(10), // Won't be used
    K(11),
    L(12),
    M(13),
    N(14),
    O(15),
    P(16),
    Q(17),
    R(18),
    S(19),
    T(20),
    U(21),
    V(22),
    W(23),
    X(24),
    Y(25),
    Z(26), // Won't be used
    SPACE(90),
    ERROR(99);

    private int letterIndex;

    private LetterClass(int letterIndex) {
        this.letterIndex = letterIndex;
    }

    public static LetterClass getLetter(int legIndex) {
        for (LetterClass l : LetterClass.values()) {
            if (l.letterIndex == legIndex) return l;
        }
        return LetterClass.ERROR;
    }

    public static int getIndex(String letter) {
        for (LetterClass l : LetterClass.values()) {
            if (l.toString().equals(letter)) return l.letterIndex;
        }
        throw new IllegalArgumentException("Index not found");
    }

    // Example usage

    //  int myLetterIndex = 1;
    //  expected : A
    //  LetterClass myLetter = LetterClass.getLetter(myLetterIndex);

}
