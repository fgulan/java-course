package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

import java.util.ArrayList;
import java.util.List;

/**
 * DrawingModelImpl represents simple drawing model used in
 * {@link JDrawingCanvas}. It implements {@link DrawingModel} interface.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class DrawingModelImpl implements DrawingModel {

    /** List of GeometricalObject instances. */
    private List<GeometricalObject> objects;
    /** List of DrawingModelListener instances. */
    private List<DrawingModelListener> listeners;

    /**
     * Constructor for DrawingModelImpl class.
     */
    public DrawingModelImpl() {
        objects = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return objects.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeometricalObject getObject(int index) {
        if (index < 0 || index >= getSize()) {
            throw new IndexOutOfBoundsException("index < 0 || index >= size");
        }
        return objects.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(GeometricalObject object) {
        if (object == null) {
            throw new IllegalArgumentException("Null reference.");
        }
        int index = objects.size();
        objects.add(object);
        notifyObjectAdded(index, index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDrawingModelListener(DrawingModelListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Null reference.");
        }
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeDrawingModelListener(DrawingModelListener listener) {
        listeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        int index = objects.size() - 1;
        objects.clear();
        if (index < 0) {
            index = 0;
        }
        notifyObjectRemoved(0, index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObject(int index) {
        if (index < 0 || index >= objects.size()) {
            return;
        }
        objects.remove(index);
        notifyObjectRemoved(index, index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateObject(int index) {
        if (index < 0 || index >= objects.size()) {
            return;
        }
        objects.get(index).update();
        notifyObjectChanged(index, index);
    }

    /**
     * Notify all listeners on new {@link GeometricalObject} objects in the
     * list.
     * 
     * @param index0
     *            Start position.
     * @param index1
     *            End position.
     */
    private void notifyObjectAdded(int index0, int index1) {
        for (DrawingModelListener l : listeners) {
            l.objectsAdded(this, index0, index1);
        }
    }

    /**
     * Notify all listeners on removing {@link GeometricalObject} objects from
     * the list.
     * 
     * @param index0
     *            Start position.
     * @param index1
     *            End position.
     */
    private void notifyObjectRemoved(int index0, int index1) {
        for (DrawingModelListener l : listeners) {
            l.objectsRemoved(this, index0, index1);
        }
    }

    /**
     * Notify all listeners on update of {@link GeometricalObject} objects in
     * the list.
     * 
     * @param index0
     *            Start position.
     * @param index1
     *            End position.
     */
    private void notifyObjectChanged(int index0, int index1) {
        for (DrawingModelListener l : listeners) {
            l.objectsChanged(this, index0, index1);
        }
    }
}
