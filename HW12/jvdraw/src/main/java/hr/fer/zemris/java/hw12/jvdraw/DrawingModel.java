package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

/**
 * Represents object which holds geometrical objects in internal list. It
 * represents Subject in observer pattern.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface DrawingModel {

    /**
     * Returns current size of the list with objects for drawing.
     * 
     * @return Current size of the list with objects for drawing.
     */
    public int getSize();

    /**
     * Returns {@link GeometricalObject} from internal list at given index.
     * 
     * @param index
     *            Input index.
     * @return {@link GeometricalObject} from internal list at given index.
     */
    public GeometricalObject getObject(int index);

    /**
     * Adds a given {@link GeometricalObject} into the internal list.
     * 
     * @param object
     *            {@link GeometricalObject} instance.
     */
    public void add(GeometricalObject object);

    /**
     * Registers a new {@link DrawingModelListener} object as a listener on
     * change in internal list.
     * 
     * @param listener
     *            {@link DrawingModelListener} instance as a listener.
     */
    public void addDrawingModelListener(DrawingModelListener listener);

    /**
     * Unregisters a given {@link DrawingModelListener} object as a listener
     * from internal list.
     * 
     * @param listener
     *            {@link DrawingModelListener} object to unregister.
     */
    public void removeDrawingModelListener(DrawingModelListener listener);

    /**
     * Removes all {@link GeometricalObject} from internal list.
     */
    public void clear();

    /**
     * Removes object at given index from internal list.
     * 
     * @param index
     *            Index in the list.
     */
    public void removeObject(int index);

    /**
     * Updates object at given index from internal list.
     * 
     * @param index
     *            Index in the list.
     */
    public void updateObject(int index);
}
