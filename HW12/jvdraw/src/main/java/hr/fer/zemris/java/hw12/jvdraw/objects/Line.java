package hr.fer.zemris.java.hw12.jvdraw.objects;

import hr.fer.zemris.java.hw12.jvdraw.panels.LinePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JOptionPane;

/**
 * Line class inherits {@link GeometricalObject} class. It represents a line
 * object.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Line extends GeometricalObject {

    /** Line color. */
    private Color color;
    /** End point. */
    private Point end;
    /** Start point. */
    private Point start;

    /**
     * Constructor for Line class. Creates line with given name, color and start
     * and end point.
     * 
     * @param name
     *            Line name.
     * @param start
     *            Start point.
     * @param end
     *            End point.
     * @param color
     *            Line color.
     */
    public Line(String name, Point start, Point end, Color color) {
        super(name);
        this.start = start;
        this.end = end;
        this.color = color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        int status = JOptionPane.showConfirmDialog(null,
                LinePanel.getPanel(this));
        if (status == JOptionPane.OK_OPTION) {
            Point newStart = null;
            Point newEnd = null;
            try {
                newStart = LinePanel.getStart();
                newEnd = LinePanel.getEnd();
            } catch (Exception ex) {
                return;
            }
            this.color = LinePanel.getForeground();
            this.start = newStart;
            this.end = newEnd;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics g, RectBounds rect) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setColor(color);
        g2.drawLine(start.x - rect.getMinX(), start.y - rect.getMinY(), end.x
                - rect.getMinX(), end.y - rect.getMinY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return String
                .format("LINE %d %d %d %d %d %d %d%n", start.x, start.y, end.x,
                        end.y, color.getRed(), color.getGreen(),
                        color.getBlue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBounds(RectBounds box) {
        box.setMinX(Math.min(start.x, end.x));
        box.setMaxX(Math.max(start.x, end.x));
        box.setMinY(Math.min(start.y, end.y));
        box.setMaxY(Math.max(start.y, end.y));
    }

    /**
     * Get current line color.
     * 
     * @return Current color,
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns end point of a line.
     * 
     * @return End point of a line.
     */
    public Point getEnd() {
        return end;
    }

    /**
     * Returns start point of a line.
     * 
     * @return Start point of a line.
     */
    public Point getStart() {
        return start;
    }

    /**
     * Sets a new color for a current line.
     * 
     * @param color
     *            New color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets a new end point for a current line.
     * 
     * @param end
     *            New end point.
     */
    public void setEnd(Point end) {
        this.end = end;
    }

    /**
     * Sets a new start point for a current line.
     * 
     * @param start
     *            New Start point.
     */
    public void setStart(Point start) {
        this.start = start;
    }
}
