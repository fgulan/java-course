package hr.fer.zemris.java.hw12.jvdraw.shape;

import hr.fer.zemris.java.hw12.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

import java.awt.Color;
import java.awt.Point;

/**
 * Represents factory class for drawing a line object on {@link JDrawingCanvas}.
 * It implements a {@link IDrawShape} interface.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class LineShape implements IDrawShape {

    /**
     * {@inheritDoc}
     */
    @Override
    public GeometricalObject createShape(Point start, Point end,
            Color foreground, Color background, int id) {
        return new Line("Line " + id, start, end, foreground);
    }
}
