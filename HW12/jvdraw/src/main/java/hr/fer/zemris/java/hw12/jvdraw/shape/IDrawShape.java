package hr.fer.zemris.java.hw12.jvdraw.shape;

import hr.fer.zemris.java.hw12.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

import java.awt.Color;
import java.awt.Point;

/**
 * Represents geometric shape for {@link JDrawingCanvas} component.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface IDrawShape {
    /**
     * Creates and returns a new geometrical object with given values.
     * 
     * @param start
     *            Start point of a shape.
     * @param end
     *            End point of a shape.
     * @param foreground
     *            Foreground color of a shape.
     * @param background
     *            Background color of a shape (if used).
     * @param id
     *            Shape id number.
     * @return A new @ GeometricalObject} shape.
     */
    public GeometricalObject createShape(Point start, Point end,
            Color foreground, Color background, int id);
}
