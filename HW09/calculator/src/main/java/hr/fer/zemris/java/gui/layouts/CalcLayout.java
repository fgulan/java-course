package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Hashtable;

/**
 * CalcLayout implements {@link LayoutManager2} interface. It represents layout
 * with 5 rows and 7 columns where each combination of row and column (x,y)
 * accepts one component, except combinations (1,2), (1,3), (1,4) and (1,5)
 * which are contained under (1,1) position.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class CalcLayout implements LayoutManager2 {

    /** Number of rows. */
    private static final int MAX_ROWS = 5;
    /** Number of columns. */
    private static final int MAX_COLUMNS = 7;
    /** Gap between two elements. */
    private int gap;
    /** Table of elements in layout. */
    private Hashtable<Component, RCPosition> components;
    /** Current dimension for element. */
    Dimension componentDimension = null;

    /**
     * Constructor for CalcLayout. Creates CalcLayout without gap between each
     * element.
     */
    public CalcLayout() {
        this(0);
    }

    /**
     * Constructor for CalcLayout. Creates CalcLayout with given gap between
     * each element.
     * 
     * @param gap
     *            Gap size between two elements.
     */
    public CalcLayout(int gap) {
        components = new Hashtable<Component, RCPosition>();
        this.gap = gap;
    }

    /**
     * {@inheritDoc}
     */
    public void addLayoutComponent(String name, Component comp) {
        // Not necessary for CalcLayout
    }

    /**
     * {@inheritDoc}
     */
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int maxWidth = parent.getWidth() - (insets.left + insets.right);
        int maxHeight = parent.getHeight() - (insets.top + insets.bottom);
        int width = 0;
        int height = 0;

        width = ((maxWidth - gap * (MAX_COLUMNS + 1)) / MAX_COLUMNS);
        height = ((maxHeight - gap * (MAX_ROWS + 1)) / MAX_ROWS);
        componentDimension = new Dimension(width, height);

        for (Component comp : parent.getComponents()) {
            RCPosition position = components.get(comp);
            int row = position.getRow();
            int column = position.getColumn();
            if (position != null) {
                int x = insets.left + column * gap + (column - 1)
                        * componentDimension.width;
                int y = insets.top + row * gap + (row - 1)
                        * componentDimension.height;
                if (row == 1 && column == 1) {
                    comp.setBounds(x, y,
                            componentDimension.width * 5 + 4 * gap,
                            componentDimension.height);
                } else {
                    comp.setBounds(x, y, componentDimension.width,
                            componentDimension.height);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public Dimension minimumLayoutSize(Container parent) {
        int width = 0;
        int height = 0;
        for (Component comp : parent.getComponents()) {
            Dimension size = comp.getMinimumSize();
            if (size != null) {
                width = Math.max(width, size.width);
                height = Math.max(height, size.height);
            }
        }
        componentDimension = new Dimension(width, height);
        return new Dimension(MAX_COLUMNS * (width + gap) + gap, MAX_ROWS
                * (height + gap) + gap);
    }

    /**
     * {@inheritDoc}
     */
    public Dimension preferredLayoutSize(Container parent) {
        int width = 0;
        int height = 0;
        for (Component comp : parent.getComponents()) {
            Dimension size = comp.getPreferredSize();
            if (size != null) {
                width = Math.max(width, size.width);
                height = Math.max(height, size.height);
            }
        }
        componentDimension = new Dimension(width, height);
        int w = MAX_COLUMNS * (width + gap) + gap;
        int h = MAX_ROWS * (height + gap) + gap;
        Dimension dimension = new Dimension(w, h);
        return dimension;
    }

    /**
     * {@inheritDoc}
     */
    public void removeLayoutComponent(Component arg0) {
        components.remove(arg0);
    }

    /**
     * {@inheritDoc}
     */
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof RCPosition) {
            RCPosition position = (RCPosition) constraints;
            if (components.containsValue(constraints)) {
                throw new IllegalArgumentException(
                        "Component at given position alredy exists!");
            } else if (position.getRow() < 1 || position.getRow() > MAX_ROWS
                    || position.getColumn() < 1
                    || position.getColumn() > MAX_COLUMNS) {
                throw new IllegalArgumentException("Invalid row/column number!");
            } else if (position.getRow() == 1
                    && (position.getColumn() > 1 && position.getColumn() < 6)) {
                throw new IllegalArgumentException("Invalid row/column number!");
            }
            components.put(comp, position);
        } else if (constraints instanceof String) {
            String[] args = ((String) constraints).split(",");
            if (args.length != 2) {
                throw new IllegalArgumentException("Given points are invalid!");
            }
            try {
                int xValue = Integer.parseInt(args[0]);
                int yValue = Integer.parseInt(args[1]);
                this.addLayoutComponent(comp, new RCPosition(xValue, yValue));
            } catch (Exception e) {
                throw new IllegalArgumentException("Given points are invalid!");
            }
        } else {
            throw new IllegalArgumentException(
                    "Invalid constraints object. It must be instance of RCPosition or String in form \"x,y\"!");
        }

    }

    /**
     * {@inheritDoc}
     */
    public float getLayoutAlignmentX(Container arg0) {
        return 0.5f;
    }

    /**
     * {@inheritDoc}
     */
    public float getLayoutAlignmentY(Container arg0) {
        return 0.5f;
    }

    /**
     * {@inheritDoc}
     */
    public void invalidateLayout(Container arg0) {
        // Not necessary for CalcLayout
    }

    /**
     * {@inheritDoc}
     */
    public Dimension maximumLayoutSize(Container parent) {
        int width = 0;
        int height = 0;
        for (Component comp : parent.getComponents()) {
            Dimension size = comp.getMaximumSize();
            if (size != null) {
                width = Math.max(width, size.width);
                height = Math.max(height, size.height);
            }
        }

        return new Dimension(MAX_COLUMNS * (width + gap) + gap, MAX_ROWS
                * (height + gap) + gap);
    }

}
