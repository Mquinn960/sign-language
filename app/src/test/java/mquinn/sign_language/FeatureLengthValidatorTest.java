package mquinn.sign_language;

import org.junit.Test;
import org.opencv.core.Mat;

import static org.junit.Assert.*;

public class FeatureLengthValidatorTest {
    @Test
    public void featureLength_isCorrect(Mat svmFeatures) {
        assertEquals(svmFeatures.size(), 2916);
    }
}