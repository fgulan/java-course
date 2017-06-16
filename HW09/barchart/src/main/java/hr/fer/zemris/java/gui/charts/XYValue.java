package hr.fer.zemris.java.gui.charts;

/**
 * Class XYValue represent position and height for {@link BarChartComponent }
 * bar.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class XYValue {

    /** X value. */
    int x;
    /** Y value. */
    int y;

    /**
     * Constructor for XYValue class.
     * 
     * @param x
     *            Number on x-axis.
     * @param y
     *            Number on y-axis.
     */
    public XYValue(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * Returns value on x-axis.
     * 
     * @return Value on x-axis.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns value on y-axis.
     * 
     * @return Value on y-axis.
     */
    public int getY() {
        return y;
    }

}
