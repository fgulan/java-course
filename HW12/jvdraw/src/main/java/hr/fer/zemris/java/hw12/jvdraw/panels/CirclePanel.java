package hr.fer.zemris.java.hw12.jvdraw.panels;

import hr.fer.zemris.java.hw12.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * {@link JPanel} factory class using singleton pattern and lazy implementation.
 * It creates a update panel for a circle object.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class CirclePanel {
    /** A update panel. */
    private static JPanel circlePanel;
    /** Circle center point. */
    private static JTextField centerPoint;
    /** Circle radius value. */
    private static JTextField radiusText;
    /** Circle foreground color. */
    private static JColorArea fgColor;

    /**
     * Constructor for CirclePanel class.
     */
    protected CirclePanel() {

    }

    /**
     * Creates and returns a new panel for changing and updating circle object.
     * It uses a lazy implementation, so once created panel is used every new
     * time when method is called.
     * 
     * @param object
     *            {@link Circle} object.
     * @return A new panel for updating a {@link Circle} object.
     */
    public static JPanel getPanel(Circle object) {
        if (circlePanel == null) {
            createCirclePanel(object);
        }
        updateCirclePanel(object);
        return circlePanel;
    }

    /**
     * Updates created panel with new values.
     * 
     * @param circle
     *            {@link Circle} object.
     */
    private static void updateCirclePanel(Circle circle) {
        centerPoint.setText(circle.getCenter().x + ", " + circle.getCenter().y);
        radiusText.setText(String.valueOf(circle.getRadius()));
        fgColor.setCurrentColor(circle.getColor());
    }

    /**
     * Creates a new panel for given {@link Circle} object.
     * 
     * @param circle
     *            {@link Circle} object.
     */
    private static void createCirclePanel(Circle circle) {
        circlePanel = new JPanel(new GridLayout(0, 2));
        centerPoint = new JTextField(circle.getCenter().x + ", "
                + circle.getCenter().y);
        radiusText = new JTextField(String.valueOf(circle.getRadius()));
        fgColor = new JColorArea(circle.getColor());

        circlePanel.add(new JLabel("Center:"));
        circlePanel.add(centerPoint);
        circlePanel.add(new JLabel("Radius"));
        circlePanel.add(radiusText);
        circlePanel.add(new JLabel("FG Color:"));
        circlePanel.add(fgColor);
    }

    /**
     * Returns a selected foreground color from {@link JColorArea} object.
     * 
     * @return Selected foreground color from {@link JColorArea} object.
     */
    public static Color getForeground() {
        if (fgColor == null) {
            return Color.WHITE;
        }
        return fgColor.getCurrentColor();
    }

    /**
     * Returns a new center point given in update panel.
     * 
     * @return New center point from update panel.
     */
    public static Point getCenter() {
        String[] input = centerPoint.getText().split(",");
        Point center = new Point(Integer.parseInt(input[0].trim()),
                Integer.parseInt(input[1].trim()));
        return center;
    }

    /**
     * Returns a new circle radius given in update panel.
     * 
     * @return A new circle radius.
     */
    public static int getRadius() {
        int radius = Integer.parseInt(radiusText.getText().trim());
        return radius;
    }
}
