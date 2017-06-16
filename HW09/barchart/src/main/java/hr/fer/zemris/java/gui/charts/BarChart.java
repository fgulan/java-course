package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * BarChart class represents graph object. It is consisted of graph elements and
 * constraints for displaying graph.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class BarChart {

    /** List of {@link XYValue} values. */
    private List<XYValue> values;
    /** Description for x-axis. */
    private String xDescription;
    /** Description for y-axis. */
    private String yDescription;

    /** Start point on y-axis. */
    private int yMin;
    /** End point on y-axis. */
    private int yMax;
    /** Distance between two points on y-axis. */
    private int yStep;

    /**
     * Constructor for BarChart class.
     * 
     * @param values
     *            List of {@link XYValue} values.
     * @param xDescription
     *            Description for x-axis.
     * @param yDescription
     *            Description for y-axis.
     * @param yMin
     *            Start point on y-axis.
     * @param yMax
     *            End point on y-axis.
     * @param yStep
     *            Distance between two points on y-axis.
     */
    public BarChart(List<XYValue> values, String xDescription,
            String yDescription, int yMin, int yMax, int yStep) {
        this.values = values;
        this.xDescription = xDescription;
        this.yDescription = yDescription;
        this.yMin = yMin;
        this.yMax = yMax;
        this.yStep = yStep;
    }

    /**
     * Returns list of {@link XYValue} values.
     * 
     * @return List of {@link XYValue} values.
     */
    public List<XYValue> getValues() {
        return values;
    }

    /**
     * Returns description for x-axis.
     * 
     * @return Description for x-axis.
     */
    public String getxDescription() {
        return xDescription;
    }

    /**
     * Returns description for y-axis.
     * 
     * @return Description for y-axis.
     */
    public String getyDescription() {
        return yDescription;
    }

    /**
     * Returns first number on y-axis.
     * 
     * @return First number on y-axis.
     */
    public int getyMin() {
        return yMin;
    }

    /**
     * Returns last number y-axis.
     * 
     * @return Last number y-axis.
     */
    public int getyMax() {
        return yMax;
    }

    /**
     * Returns distance between two points on y-axis.
     * 
     * @return Distance between two points on y-axis.
     */
    public int getyStep() {
        return yStep;
    }

}
