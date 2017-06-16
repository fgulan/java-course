package hr.fer.zemris.java.hw12.jvdraw.objects;

import hr.fer.zemris.java.hw12.jvdraw.panels.FilledCirclePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JOptionPane;

/**
 * FilledCircle class represents a filled circle object. It extends
 * {@link Circle} class.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class FilledCircle extends Circle {

    /** Filled circle fill color. */
    private Color background;

    /**
     * Constructor for FilledCircle class. It creates a filled circle with given
     * parameters.
     * 
     * @param name
     *            Name of an object.
     * @param center
     *            Circle center point.
     * @param radius
     *            Circle radius.
     * @param foreground
     *            Circle foreground color.
     * @param background
     *            Circle fill color.
     */
    public FilledCircle(String name, Point center, int radius,
            Color foreground, Color background) {
        super(name, center, radius, foreground);
        this.background = background;
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
        g2.setColor(background);

        g2.fillOval(center.x - radius - rect.getMinX(), center.y - radius
                - rect.getMinY(), 2 * radius, 2 * radius);
        super.draw(g2, rect);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        int status = JOptionPane.showConfirmDialog(null,
                FilledCirclePanel.getPanel(this));

        if (status == JOptionPane.OK_OPTION) {
            Point newCenter = null;
            int newRadius = 0;
            try {
                newCenter = FilledCirclePanel.getCenter();
                newRadius = FilledCirclePanel.getRadius();
            } catch (Exception ex) {
                return;
            }
            setColor(FilledCirclePanel.getForeground());
            background = FilledCirclePanel.getBackground();
            setCenter(newCenter);
            setRadius(newRadius);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return String.format("FCIRCLE %d %d %d %d %d %d %d %d %d%n", center.x,
                center.y, radius, color.getRed(), color.getGreen(),
                color.getBlue(), background.getRed(), background.getGreen(),
                background.getBlue());
    }

    /**
     * Gets a current circle fill color.
     * 
     * @return Current circle fill color.
     */
    public Color getBackground() {
        return background;
    }

    /**
     * Sets a new circle fill color.
     * 
     * @param background
     *            New background color.
     */
    public void setBackground(Color background) {
        this.background = background;
    }

}
