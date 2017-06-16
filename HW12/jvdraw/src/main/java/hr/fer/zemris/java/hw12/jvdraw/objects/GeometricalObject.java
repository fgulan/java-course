package hr.fer.zemris.java.hw12.jvdraw.objects;

import hr.fer.zemris.java.hw12.jvdraw.components.JDrawingCanvas;

import java.awt.Graphics;

/**
 * Represents an geometrical object which has ability to draw itself on a
 * {@link JDrawingCanvas} object.
 * 
 * @author Filip Gulan
 * @version 1.0
 * 
 */
public abstract class GeometricalObject {
    /** Name of an object. */
    private String name;

    /**
     * Constructor for GeometricalObject class. It accepts a name of an object.
     * 
     * @param name
     *            Object name.
     */
    public GeometricalObject(String name) {
        this.name = name;
    }

    /**
     * Updates current object.
     */
    public abstract void update();

    /**
     * Draws current object on to given {@link Graphics} object, scaled with
     * given {@link RectBounds} object.
     * 
     * @param g
     *            {@link Graphics} object.
     * @param rect
     *            {@link RectBounds} object.
     */
    public abstract void draw(Graphics g, RectBounds rect);

    /**
     * Returns a description of a current {@link GeometricalObject} object.
     * 
     * @return A description of a current {@link GeometricalObject} object.
     */
    public abstract String getDescription();

    /**
     * Sets a given bounds based on current object dimensions.
     * 
     * @param rect
     *            {@link RectBounds} object to fill with current object
     *            rectangle boundaries.
     */
    public abstract void setBounds(RectBounds rect);

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name;
    }
}
