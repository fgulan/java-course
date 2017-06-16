package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

import javax.swing.AbstractListModel;
import javax.swing.JList;

/**
 * DrawingObjectListModel represents list model for {@link JList} component. It
 * extends {@link AbstractListModel} class and implements
 * {@link DrawingModelListener} interface.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class DrawingObjectListModel extends
        AbstractListModel<GeometricalObject> implements DrawingModelListener {

    /** Serial number. */
    private static final long serialVersionUID = -7592248021587800082L;
    /** Drawing model. */
    private DrawingModel model;

    /**
     * Constructor for DrawingObjectListModel class. It creates a
     * DrawingObjectListModel object with given {@link DrawingModel} object as
     * data source.
     * 
     * @param model
     *            {@link DrawingModel} object as data source.
     */
    public DrawingObjectListModel(DrawingModel model) {
        super();
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeometricalObject getElementAt(int index) {
        return model.getObject(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return model.getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        fireIntervalAdded(this, index0, index1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        fireIntervalRemoved(this, index0, index1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        fireContentsChanged(this, index0, index1);
    }

}
