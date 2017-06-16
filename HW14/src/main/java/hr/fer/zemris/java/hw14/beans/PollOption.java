package hr.fer.zemris.java.hw14.beans;

/**
 * PollEntry class represents an each element of a poll in voting process, or
 * row in a database table.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class PollOption implements Comparable<PollOption> {
    /** Poll entry id. */
    private long id;
    /** Poll entry name. */
    private String name;
    /** Poll entry link. */
    private String link;
    /** Number of votes. */
    private int votes;

    /**
     * Constructor for PollEntry class. It creates poll entry with given
     * parameters.
     * 
     * @param id Entry ID.
     * @param name Entry name.
     * @param link Entry web link.
     * @param votes Number of votes.
     */
    public PollOption(long id, String name, String link, int votes) {
        super();
        this.id = id;
        this.name = name;
        this.link = link;
        this.votes = votes;
    }

    /**
     * Returns current entry name.
     * 
     * @return Current entry name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for current entry.
     * 
     * @param name New name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns current entry web link.
     * 
     * @return Current entry web link.
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets a new web link for current entry.
     * 
     * @param link New web link.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Returns current entry number of votes.
     * 
     * @return Current entry number of votes.
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Sets a new number of votes for current entry.
     * 
     * @param votes New number of votes.
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    /**
     * Returns current entry ID number.
     * 
     * @return Current entry ID number.
     */
    public long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(PollOption o) {
        return Integer.compare(o.getVotes(), this.getVotes());
    }

}
