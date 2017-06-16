package hr.fer.zemris.java.hw12.jvdraw.components;

import hr.fer.zemris.java.hw12.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.DrawingModelListener;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.RectBounds;
import hr.fer.zemris.java.hw12.jvdraw.shape.IDrawShape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * JDrawingCanvas represents drawing area. It implements
 * {@link DrawingModelListener} interface and extends {@link JComponent} class.
 * Communicates with components on it through {@link DrawingModel} interface.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

    /** Serial number. */
    private static final long serialVersionUID = 3725641262122486882L;
    /** Mouse start point. */
    private Point mouseStart;
    /** Mouse end point. */
    private Point mouseEnd;
    /** Foreground color provider. */
    private IColorProvider fgProvider;
    /** Background color provider. */
    private IColorProvider bgProvider;
    /** Change status. */
    private boolean changed;
    /** Current geometrical object. */
    private GeometricalObject currentObject;
    /** Currently selected shape. */
    private IDrawShape selectedShape;
    /** Drawing model. */
    private DrawingModel model;
    /** Current id of an object. */
    private int id;

    /**
     * Constructor for JDrawingCanvas object. It creates canvas object with
     * given parameters.
     * 
     * @param fgProvider
     *            Foreground color provider.
     * @param bgProvider
     *            Background color provider.
     * @param model
     *            Drawing model.
     */
    public JDrawingCanvas(IColorProvider fgProvider, IColorProvider bgProvider,
            DrawingModel model) {
        this.fgProvider = fgProvider;
        this.bgProvider = bgProvider;
        this.model = model;

        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    /** Mouse listener. */
    private MouseAdapter mouseListener = new MouseAdapter() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (selectedShape == null) {
                return;
            }
            if (mouseStart == null) {
                mouseStart = e.getPoint();
            } else {
                mouseEnd = e.getPoint();
                addNewShape(true);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            if (mouseStart == null) {
                return;
            }
            mouseEnd = e.getPoint();
            addNewShape(false);
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (model != null) {
            for (int i = 0, size = model.getSize(); i < size; i++) {
                model.getObject(i).draw(g, RectBounds.NO_BOUNDS);
            }
        }

        if (currentObject != null) {
            currentObject.draw(g, RectBounds.NO_BOUNDS);
        }
    }

    /**
     * Adds a new shape onto canvas.
     * 
     * @param mouseClicked
     *            If <code>true</code> adds permanently shape to the canvas,
     *            otherwise only shows where object would be.
     */
    public void addNewShape(boolean mouseClicked) {
        currentObject = selectedShape.createShape(mouseStart, mouseEnd,
                fgProvider.getCurrentColor(), bgProvider.getCurrentColor(),
                id + 1);
        if (mouseClicked) {
            model.add(currentObject);
            id++;
            resetCanvas();
        } else {
            repaint();
        }
    }

    /**
     * Reset canvas state.
     */
    private void resetCanvas() {
        mouseStart = null;
        mouseEnd = null;
        currentObject = null;
    }

    /**
     * Checks if there is new change in canvas area.
     * 
     * @return <code>true</code> if canvas is changed since last saving,
     *         <code>false</code> otherwise.
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * Sets canvas changed status.
     * 
     * @param changed
     *            New status.
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * Sets a new selected shape.
     * 
     * @param selectedShape
     *            New shape to draw.
     */
    public void setSelectedShape(IDrawShape selectedShape) {
        this.selectedShape = selectedShape;
        resetCanvas();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        this.changed = true;
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        this.changed = true;
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        this.changed = true;
        repaint();
    }
}
