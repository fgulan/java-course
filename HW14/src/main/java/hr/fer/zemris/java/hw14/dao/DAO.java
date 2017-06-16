package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.beans.Poll;
import hr.fer.zemris.java.hw14.beans.PollOption;

import java.util.List;

/**
 * DAO interface represents data access object for voting database. It is used
 * for retrieving and updating poll and poll options in current database.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public interface DAO {

    /**
     * Returns all available polls currently stored in database.
     * 
     * @return All available polls currently stored in database.
     */
    public List<Poll> getAllPolls();

    /**
     * Returns ID for poll with given title.
     * 
     * @param title Poll title.
     * @return Poll ID if poll exists in database, -1 otherwise.
     */
    public long getPollID(String title);

    /**
     * Returns {@link Poll} instance of poll stored in database with given ID.
     * 
     * @param id Poll ID.
     * @return {@link Poll} instance of poll stored in database with given ID.
     */
    public Poll getPoll(long id);

    /**
     * Adds a new poll in database with given title and description.
     * 
     * @param title Poll title.
     * @param description Poll description.
     * @return Generated ID for new poll.
     */
    public long addPoll(String title, String description);

    /**
     * Returns list of all poll options for poll in database with given poll id.
     * 
     * @param pollId Poll ID.
     * @return List of all poll options for poll in database with given poll id.
     */
    public List<PollOption> getPollOptions(long pollId);

    /**
     * Adds a new poll option to a poll options table in current database with
     * given parameters.
     * 
     * @param title Poll option title.
     * @param link Poll option web link.
     * @param pollID Poll id.
     * @param votes Number of votes.
     * @return Generated ID for new poll option.
     */
    public long addPollOption(String title, String link, long pollID, int votes);

    /**
     * Returns number of votes for given poll option in given poll.
     * 
     * @param pollID Poll ID.
     * @param optionID Poll option ID.
     * @return Number of votes.
     */
    public Integer getOptionVotes(long pollID, long optionID);

    /**
     * Sets a new number of votes for given poll option in given poll.
     * 
     * @param pollID Poll ID.
     * @param optionID Poll option ID.
     * @param votes New number of votes.
     */
    public void setOptionVotes(long pollID, long optionID, int votes);

    /**
     * Checks if poll option with given title and poll ID exists in current
     * database.
     * 
     * @param title Poll option title.
     * @param pollID Poll ID.
     * @return <code>true</code> if poll option with given name and poll id
     *         exist, <code>false</code> otherwise.
     */
    boolean isPollOptionPresent(String title, long pollID);
}
