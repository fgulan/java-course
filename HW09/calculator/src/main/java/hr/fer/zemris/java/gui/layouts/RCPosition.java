package hr.fer.zemris.java.gui.layouts;

/**
 * RCPosition object represent constraints for {@link CalcLayout} layout
 * manager.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class RCPosition {

    /** Row index. */
    private int row;
    /** Column index */
    private int column;

    /**
     * Constructor for RCPosition class.
     * 
     * @param row
     *            Row index.
     * @param column
     *            Column index.
     */
    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns row index.
     * 
     * @return Row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns column index.
     * 
     * @return Column index.
     */
    public int getColumn() {
        return column;
    }

    @Override
    /**
     * {@InheritDoc}
     */
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof RCPosition) {
            result = ((RCPosition) obj).column == column
                    && ((RCPosition) obj).row == row;
        }
        return result;
    }
}
