package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * BarChartComponent component represents graph component. On given container
 * draws coordinate system with horizontal and vertical lines. It extends
 * {@link JComponent} class.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class BarChartComponent extends JComponent {

    /** Serial. */
    private static final long serialVersionUID = -2290688286165650352L;
    /** Spacing. */
    private static final int SPACE = 10;
    /** Chart data. */
    private BarChart chart;

    /** Number of vertical lines. */
    private int vLines;
    /** Distance between two vertical lines. */
    private int vStep;
    /** Number of horizontal lines. */
    private int hLines;
    /** Distance between two horizontal lines. */
    private int hStep;
    /** Rectangle object. */
    private Rectangle rect;
    /** Lower left point on x-axis. */
    private int xPoint;
    /** Lower left point on y-axis. */
    private int yPoint;
    /** Text height. */
    private int textHeight;
    /** Preferred size. */
    private Dimension preferredSize;

    /**
     * Constructor for BarChartComponent.
     * 
     * @param chart
     *            BarChart reference.
     */
    public BarChartComponent(BarChart chart) {
        this.chart = chart;
        int width = getNumOfLinesV() * 150;
        int height = getNumOfLinesH() * 40;
        preferredSize = new Dimension(width, height);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Dimension getPreferredSize() {
        return preferredSize;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void paintComponent(Graphics g) {
        textHeight = getTextHeight(g);
        int numberWidth = getNumberWidthY(g);
        xPoint = SPACE + textHeight + SPACE + numberWidth + SPACE;
        yPoint = SPACE + textHeight + SPACE + textHeight + SPACE;

        Insets insets = getInsets();
        Dimension size = getSize();
        rect = new Rectangle(insets.left, insets.top, size.width - insets.left
                - insets.right, size.height - insets.top - insets.bottom);

        vLines = getNumOfLinesV();
        vStep = getStepV();
        hLines = getNumOfLinesH();
        hStep = getStepH();

        drawHLines(g);
        drawVLines(g);
        drawBars(g);
        printDescription(g);
        printNumbersY(g);
        printNumbersX(g);
    }

    /**
     * Calculates text width.
     * 
     * @param text
     *            Input text.
     * @param g
     *            The {@link Graphics} object.
     * @return Text width.
     */
    private int getTextWidth(String text, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        return fm.stringWidth(text);
    }

    /**
     * Calculates text height on current component.
     * 
     * @param g
     *            The {@link Graphics} object.
     * @return Text height on current component.
     */
    private int getTextHeight(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        return fm.getHeight();
    }

    /**
     * Calculates max number width for y-axis numeration.
     * 
     * @param g
     *            The {@link Graphics} object.
     * @return Maximum number width.
     */
    private int getNumberWidthY(Graphics g) {
        FontMetrics fm = g.getFontMetrics();

        int dimension = 0;
        int yValueStart = chart.getyMin();
        int yValueEnd = chart.getyMax();
        int yValue = Math.max(Math.abs(yValueEnd), Math.abs(yValueStart));

        int width = fm.stringWidth(Integer.toString(yValue));
        dimension = Math.max(width, dimension);

        return dimension;
    }

    /**
     * Draws numbers on y-axis.
     * 
     * @param g
     *            The {@link Graphics} object.
     */
    private void printNumbersY(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.black);
        Font currentFont = g.getFont();
        g.setFont(new Font(currentFont.toString(), Font.BOLD, currentFont
                .getSize()));

        for (int i = 0; i < hLines; i++) {
            String text = Integer.toString(chart.getyMin() + i
                    * chart.getyStep());
            g.drawString(text, xPoint - fm.stringWidth(text) - SPACE,
                    rect.height - yPoint - i * hStep + (textHeight / 4));
        }

        g.setFont(currentFont);
    }

    /**
     * Draws numbers on y-axis.
     * 
     * @param g
     *            The {@link Graphics} object.
     */
    private void printNumbersX(Graphics g) {
        g.setColor(Color.black);
        Font currentFont = g.getFont();
        g.setFont(new Font(currentFont.toString(), Font.BOLD, currentFont
                .getSize()));

        for (int i = 0; i < vLines; i++) {
            XYValue value = chart.getValues().get(i);
            String text = Integer.toString(value.getX());
            int x = xPoint + vStep / 2 - getTextWidth(text, g) / 2;
            g.drawString(text, x + i * vStep, rect.height - yPoint + SPACE * 2);
        }

        g.setFont(currentFont);
    }

    /**
     * Draws horizontal lines of graph on component.
     * 
     * @param g
     *            The {@link Graphics} object.
     */
    private void drawHLines(Graphics g) {
        for (int i = 0; i < hLines; i++) {
            if (i == 0) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(xPoint - 5, rect.height - yPoint, rect.width
                        - SPACE, rect.height - yPoint);

                // Draw triangle
                int x[] = new int[3];
                int y[] = new int[3];
                x[1] = x[0] = rect.width - SPACE;
                x[2] = rect.width - SPACE + 5;
                y[0] = rect.height - yPoint - 5;
                y[1] = rect.height - yPoint + 5;
                y[2] = rect.height - yPoint;
                drawTriangle(x, y, g);
                g.setColor(Color.LIGHT_GRAY);
                continue;
            }
            g.drawLine(xPoint, rect.height - yPoint - i * hStep, rect.width
                    - SPACE, rect.height - yPoint - i * hStep);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(xPoint - SPACE / 2, rect.height - yPoint - i * hStep,
                    xPoint, rect.height - yPoint - i * hStep);
        }
    }

    /**
     * Draws a filled triangle at given points.
     * 
     * @param x
     *            Points on x-axis.
     * @param y
     *            Points on y-axis.
     * @param g
     *            The {@link Graphics} object.
     */
    private void drawTriangle(int[] x, int[] y, Graphics g) {
        int n = 3;
        Polygon p = new Polygon(x, y, n);
        g.setColor(Color.GRAY);
        g.fillPolygon(p);
    }

    /**
     * Draws vertical lines of graph on component.
     * 
     * @param g
     *            The {@link Graphics} object.
     */
    private void drawVLines(Graphics g) {
        int yEnd = (hLines - 1) * hStep;
        Graphics2D g2d;
        for (int i = 0; i <= vLines; i++) {
            if (i == 0) {
                g2d = (Graphics2D) g.create();
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(xPoint, rect.height - yPoint - yEnd - SPACE,
                        xPoint, rect.height - yPoint + 5);

                // Draw triangle
                int x[] = new int[3];
                int y[] = new int[3];
                x[0] = xPoint + 6;
                x[1] = xPoint - 7;
                x[2] = xPoint - 1;
                y[1] = y[0] = rect.height - yPoint - yEnd - SPACE + 6;
                y[2] = rect.height - yPoint - yEnd - SPACE-2;
                drawTriangle(x, y, g);
                g.setColor(Color.LIGHT_GRAY);
                continue;
            }
            
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(xPoint + i * vStep, rect.height - yPoint - yEnd - SPACE,
                    xPoint + i * vStep, rect.height - yPoint - 2);
            
            g2d = (Graphics2D) g.create();
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(xPoint + i * vStep, rect.height - yPoint, xPoint + i
                    * vStep, rect.height - yPoint + 5);
            g2d.dispose();
        }
    }

    /**
     * Draws graph bars on component
     * 
     * @param g
     *            The {@link Graphics} object.
     */
    private void drawBars(Graphics g) {

        for (int i = 0; i < vLines; i++) {
            int yValue = chart.getValues().get(i).getY();

            // Orange color
            g.setColor(new Color(245, 121, 49));
            g.fillRect(xPoint + 1 + i * vStep, (rect.height - yPoint - yValue
                    * hStep / chart.getyStep()), vStep - 1, yValue * hStep
                    / chart.getyStep() - 1);

            // Shadow
            g.setColor(new Color(0.6509f, 0.6588f, 0.6275f, 0.6f));
            g.fillRect(xPoint + 2 + (i + 1) * vStep, (rect.height - yPoint
                    - yValue * hStep / chart.getyStep() + 5), 5, yValue * hStep
                    / chart.getyStep() - 10);
        }
    }

    /**
     * Draws description for each axis.
     * 
     * @param g
     *            The {@link Graphics} object.
     */
    private void printDescription(Graphics g) {
        String text = chart.getxDescription();
        FontMetrics fm = g.getFontMetrics();
        int width = getTextWidth(text, g);

        // Draws description for x-axis
        g.setColor(Color.black);
        g.drawString(text, rect.x + (rect.width - width) / 2, rect.height
                - SPACE);

        // Draws description for y-axis
        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform defaultAt = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(3 * Math.PI / 2);

        g2d.setTransform(at);
        g2d.setColor(Color.black);

        text = chart.getyDescription();
        width = fm.stringWidth(text);

        g2d.drawString(text,
                -(rect.y + (rect.height + width * 2 + SPACE) / 2 - yPoint),
                SPACE * 2);
        g2d.setTransform(defaultAt);
        g2d.dispose();
    }

    /**
     * Calculates distance between two vertical lines.
     * 
     * @return Distance between two vertical lines.
     */
    private int getStepV() {
        int numberOfLines = getNumOfLinesV();
        int step = (rect.width - (xPoint + SPACE)) / numberOfLines;
        return step;
    }

    /**
     * Computes number of vertical lines.
     * 
     * @return Number of vertical lines.
     */
    private int getNumOfLinesV() {
        int numberOfLines = chart.getValues().size();
        return numberOfLines;
    }

    /**
     * Calculates distance between two horizontal lines.
     * 
     * @return Distance between two horizontal lines.
     */
    private int getStepH() {
        int numberOfLines = getNumOfLinesH();
        int step = (rect.height + SPACE - yPoint) / numberOfLines;
        return step;
    }

    /**
     * Computes number of horizontal lines.
     * 
     * @return Number of horizontal lines.
     */
    private int getNumOfLinesH() {
        double number = (chart.getyMax() - chart.getyMin())
                / (double) chart.getyStep();
        int numberOfLines = (int) Math.ceil(number) + 1;
        return numberOfLines;
    }

}
