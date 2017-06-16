package hr.fer.zemris.java.hw12.jvdraw.shape;

import hr.fer.zemris.java.hw12.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

import java.awt.Color;
import java.awt.Point;

/**
 * Represents factory class for drawing a circle object on
 * {@link JDrawingCanvas}. It implements a {@link IDrawShape} interface.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class CircleShape implements IDrawShape {

    /**
     * {@inheritDoc}
     */
    @Override
    public GeometricalObject createShape(Point start, Point end,
            Color foreground, Color background, int id) {
        int radius = (int) (Math.pow((start.x - end.x), 2) + Math.pow(
                (start.y - end.y), 2));
        radius = (int) Math.sqrt(radius);
        return new Circle("Circle " + id, start, radius, foreground);
    }

}
