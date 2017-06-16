package hr.fer.zemris.java.hw13.beans;

/**
 * Represents trigonometric function over given value.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class TrigonometricFun {
    /** Sine value. */
    private Double sinValue;
    /** Cosine value. */
    private Double cosValue;
    /** Input value. */
    private int value;

    /**
     * Constructor for Function class.
     * 
     * @param value Input value.
     */
    public TrigonometricFun(Integer value) {
        double angle = value * Math.PI / 180;
        this.value = value;
        sinValue = Math.sin(angle);
        cosValue = Math.cos(angle);
    }

    /**
     * Returns sine value.
     * 
     * @return Sine value.
     */
    public Double getSinValue() {
        return sinValue;
    }

    /**
     * Returns cosine value.
     * 
     * @return Cosine value.
     */
    public Double getCosValue() {
        return cosValue;
    }

    /**
     * Returns current value.
     * 
     * @return Current value.
     */
    public int getValue() {
        return value;
    }
}