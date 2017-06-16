package hr.fer.zemris.java.hw12.jvdraw.objects;

/**
 * RectBounds class represents rectangle boundaries for drawing a geometric
 * shape on @ JDrawingCanvas}.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class RectBounds {

    /** No bounds. */
    public final static RectBounds NO_BOUNDS = new RectBounds(0, 0, 0, 0);
    /** Minimal x point. */
    private int minX;
    /** Minimal y point. */
    private int minY;
    /** Maximal x point. */
    private int maxX;
    /** Maximal y point. */
    private int maxY;

    /**
     * Constructor for RectBounds class. Creates a new rectangle bounds for
     * drawing a geometrical object.
     * 
     * @param minX
     *            Minimal x point.
     * @param minY
     *            Minimal y point
     * @param maxX
     *            Maximal x point.
     * @param maxY
     *            Maximal y point.
     */
    public RectBounds(int minX, int minY, int maxX, int maxY) {
        super();
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Returns minimal x point.
     * 
     * @return Minimal x point.
     */
    public int getMinX() {
        return minX;
    }

    /**
     * Sets a new minimal x point if given value is less than current minimal x
     * point.
     * 
     * @param minX
     *            New minimal x value.
     */
    public void setMinX(int minX) {
        this.minX = Math.min(minX, this.minX);
    }

    /**
     * Returns minimal y point.
     * 
     * @return Minimal y point.
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Sets a new minimal y point if given value is less than current minimal y
     * value.
     * 
     * @param minY
     *            New minimal y value.
     */
    public void setMinY(int minY) {
        this.minY = Math.min(minY, this.minY);
    }

    /**
     * Returns maximal x point.
     * 
     * @return Maximal x point.
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * Sets a new maximal x point if given value is greater than current maximal
     * x value.
     * 
     * @param maxX
     *            New maximal x value.
     */
    public void setMaxX(int maxX) {
        this.maxX = Math.max(maxX, this.maxX);
    }

    /**
     * Returns maximal y point.
     * 
     * @return Maximal y point.
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Sets a new maximal y point if given value is greater than current maximal
     * y value.
     * 
     * @param maxY
     *            New maximal y value.
     */
    public void setMaxY(int maxY) {
        this.maxY = Math.max(maxY, this.maxY);
    }
}
