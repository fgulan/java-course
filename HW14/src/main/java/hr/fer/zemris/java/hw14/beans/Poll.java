package hr.fer.zemris.java.hw14.beans;

/**
 * Polls class represents an entry in polls table. It contains information about
 * current poll.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class Poll {
    /** Current poll id. */
    private long id;
    /** Poll title. */
    private String title;
    /** Poll description. */
    private String description;

    /**
     * Constructor for Polls class. It creates poll with given parameters.
     * 
     * @param id Poll id number.
     * @param title Poll title.
     * @param description Poll description.
     */
    public Poll(long id, String title, String description) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
    }

    /**
     * Returns current poll title.
     * 
     * @return Current poll title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new title for current poll.
     * 
     * @param title New title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns current poll description.
     * 
     * @return Current poll description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a new poll description.
     * 
     * @param description New poll description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns current poll ID number.
     * 
     * @return Current poll ID number.
     */
    public long getId() {
        return id;
    }

}
