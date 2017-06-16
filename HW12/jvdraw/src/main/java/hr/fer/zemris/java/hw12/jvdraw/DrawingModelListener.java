package hr.fer.zemris.java.hw12.jvdraw;

/**
 * Represents a listener on {@link DrawingModel} object.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface DrawingModelListener {
    /**
     * Fire when a new object or objects are added into a {@link DrawingModel}
     * object.
     * 
     * @param source
     *            {@link DrawingModel} object.
     * @param index0
     *            Start index in the list. Index of a first new added element.
     * @param index1
     *            End index in the list. Index of a last new added element.
     */
    public void objectsAdded(DrawingModel source, int index0, int index1);

    /**
     * Fire when a new object or objects are removed from {@link DrawingModel}
     * object.
     * 
     * @param source
     *            {@link DrawingModel} object.
     * @param index0
     *            Start index in the list. Index of a first removed element.
     * @param index1
     *            End index in the list. Index of a last removed element.
     */
    public void objectsRemoved(DrawingModel source, int index0, int index1);

    /**
     * Fire when object or objects are changed in a {@link DrawingModel} object.
     * 
     * @param source
     *            {@link DrawingModel} object.
     * @param index0
     *            Start index in the list. Index of a first changed element.
     * @param index1
     *            End index in the list. Index of a last changed element.
     */
    public void objectsChanged(DrawingModel source, int index0, int index1);
}
