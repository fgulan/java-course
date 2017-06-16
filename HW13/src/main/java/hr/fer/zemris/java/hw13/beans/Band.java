package hr.fer.zemris.java.hw13.beans;

/**
 * Band class represents a band in voting process. It is consisted of its name, id, link to a song
 * and number of currently gained votes.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Band {

    /** Band name. */
    private String name;
    /** Link to a song. */
    private String link;
    /** Number of votes. */
    private Integer votes;
    /** Band id. */
    private Integer id;

    /**
     * Constructor for Band class. It represents a band in voting process.
     * 
     * @param name Band name.
     * @param link Link to a band's song.
     * @param votes Number of votes for current band.
     * @param id Current band id.
     */
    public Band(String name, String link, Integer votes, Integer id) {
        super();
        this.name = name;
        this.link = link;
        this.votes = votes;
        this.id = id;
    }

    /**
     * Returns current band name.
     * 
     * @return Current band name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns current band song link.
     * 
     * @return Current band song link.
     */
    public String getLink() {
        return link;
    }

    /**
     * Returns current band number of votes.
     * 
     * @return Current band number of votes.
     */
    public Integer getVotes() {
        return votes;
    }

    /**
     * Returns current band ID number.
     * 
     * @return Current band ID number.
     */
    public Integer getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Band other = (Band) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}