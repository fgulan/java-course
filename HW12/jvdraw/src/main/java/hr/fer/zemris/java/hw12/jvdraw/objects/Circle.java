package hr.fer.zemris.java.hw12.jvdraw.objects;

import hr.fer.zemris.java.hw12.jvdraw.panels.CirclePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JOptionPane;

/**
 * Circle class represents circle object. It extends {@link GeometricalObject}
 * abstract class.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Circle extends GeometricalObject {

    /** Circle center point. */
    protected Point center;
    /** Circle radius. */
    protected int radius;
    /** Circle foreground color. */
    protected Color color;

    /**
     * Constructor for Circle class. It creates circle object with given
     * parameters.
     * 
     * @param name
     *            Circle name.
     * @param center
     *            Circle center point.
     * @param radius
     *            Circle radius.
     * @param color
     *            Circle foreground color.
     */
    public Circle(String name, Point center, int radius, Color color) {
        super(name);
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        int status = JOptionPane.showConfirmDialog(null,
                CirclePanel.getPanel(this), "Set properties",
                JOptionPane.OK_CANCEL_OPTION);
        if (status == JOptionPane.OK_OPTION) {
            Point newCenter = null;
            int newRadius = 0;
            try {
                newCenter = CirclePanel.getCenter();
                newRadius = CirclePanel.getRadius();
            } catch (Exception ex) {
                return;
            }
            this.color = CirclePanel.getForeground();
            this.center = newCenter;
            this.radius = newRadius;
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
        g2.drawOval(center.x - radius - rect.getMinX(), center.y - radius
                - rect.getMinY(), 2 * radius, 2 * radius);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return String.format("CIRCLE %d %d %d %d %d %d%n", center.x, center.y,
                radius, color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBounds(RectBounds box) {
        box.setMinX(center.x - radius);
        box.setMaxX(center.x + radius);
        box.setMinY(center.y - radius);
        box.setMaxY(center.y + radius);
    }

    /**
     * Returns center point of this Circle object.
     * 
     * @return Center point.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns radius value of this Circle object.
     * 
     * @return Radius value.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Returns current color of this Circle object.
     * 
     * @return Current color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets a new center point for this Circle object.
     * 
     * @param center
     *            New center point.
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * Sets a new radius value for this Circle object.
     * 
     * @param radius
     *            New radius value.
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Sets a new foreground color for this Circle object.
     * 
     * @param color
     *            New foreground color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

}
