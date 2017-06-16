package hr.fer.zemris.java.hw14.dao.sql;

import hr.fer.zemris.java.hw14.beans.Poll;
import hr.fer.zemris.java.hw14.beans.PollOption;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SQLDao implements {@link DAO} interface. SQLDao represents data access object
 * used in relation database system with SQL as query language. In current case
 * it is implemented for retrieving and updating voting database.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class SQLDao implements DAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Poll> getAllPolls() {
        List<Poll> polls = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        try (PreparedStatement pst = con
                .prepareStatement("SELECT id, title FROM Polls ORDER BY id")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    Poll data = new Poll(rs.getLong(1), rs.getString(2), "");
                    polls.add(data);
                }
            }
        } catch (Exception ex) {
            throw new DAOException(
                    "Unable to get all polls.", ex);
        }
        return polls;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll getPoll(long id) {
        Poll poll = null;
        Connection con = SQLConnectionProvider.getConnection();
        try (PreparedStatement pst = con
                .prepareStatement("SELECT id, title, message FROM Polls WHERE id=?")) {
            pst.setLong(1, Long.valueOf(id));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    poll = new Poll(rs.getLong(1), rs.getString(2),
                            rs.getString(3));
                }
            }
        } catch (Exception ex) {
            throw new DAOException(
                    "Unable to get poll.", ex);
        }
        return poll;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long addPoll(String title, String message) {
        long id = -1;
        Connection con = SQLConnectionProvider.getConnection();
        try (PreparedStatement pst = con.prepareStatement(
                "INSERT INTO Polls (title, message) VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, title);
            pst.setString(2, message);
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs != null && rs.next()) {
                    id = rs.getLong(1);
                }
            }
        } catch (Exception ex) {
            throw new DAOException(
                    "Unable to insert into polls table.", ex);
        }
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PollOption> getPollOptions(long id) {
        List<PollOption> pollOptions = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        try (PreparedStatement pst = con
                .prepareStatement("SELECT id, optionTitle, optionLink, votesCount "
                        + "FROM PollOptions WHERE pollID=? ORDER BY id")) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    PollOption option = new PollOption(rs.getLong(1),
                            rs.getString(2), rs.getString(3), rs.getInt(4));
                    pollOptions.add(option);
                }
            }
        } catch (Exception ex) {
            throw new DAOException(
                    "Unable to get poll options.", ex);
        }
        Collections.sort(pollOptions);
        return pollOptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long addPollOption(String title, String link, long pollID, int votes) {
        Connection con = SQLConnectionProvider.getConnection();
        long id = -1;
        try (PreparedStatement pst = con
                .prepareStatement(
                        "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, title);
            pst.setString(2, link);
            pst.setLong(3, pollID);
            pst.setInt(4, votes);
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs != null && rs.next()) {
                    id = rs.getLong(1);
                }
            }
        } catch (Exception ex) {
            throw new DAOException(
                    "Unable to insert into poll options.", ex);
        }
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getOptionVotes(long pollID, long optionID) {
        Integer data = null;
        Connection con = SQLConnectionProvider.getConnection();
        try (PreparedStatement pst = con
                .prepareStatement("SELECT votesCount FROM PollOptions WHERE pollID=? AND id=?")) {
            pst.setLong(1, Long.valueOf(pollID));
            pst.setLong(2, optionID);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    data = Integer.valueOf(rs.getInt(1));
                }
            }
        } catch (Exception ex) {
            throw new DAOException(
                    "Unable to get votes from poll options table.", ex);
        }
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOptionVotes(long pollID, long optionID, int count) {
        Connection con = SQLConnectionProvider.getConnection();
        try (PreparedStatement pst = con
                .prepareStatement("UPDATE pollOptions SET votesCount=? WHERE pollID=? AND id=?")) {
            pst.setLong(1, count);
            pst.setLong(2, pollID);
            pst.setLong(3, optionID);
            pst.executeUpdate();
        } catch (Exception ex) {
            throw new DAOException("Unable to update poll options table.", ex);
        }
    }

    @Override
    public boolean isPollOptionPresent(String title, long pollID) {
        boolean exists = false;
        Connection con = SQLConnectionProvider.getConnection();
        try (PreparedStatement pst = con
                .prepareStatement("SELECT id FROM PollOptions WHERE optiontitle=? AND pollID=?")) {
            pst.setString(1, title);
            pst.setLong(2, pollID);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    exists = true;
                    break;
                }
            }
        } catch (Exception ex) {
            throw new DAOException("Unable to get poll options.", ex);
        }
        return exists;
    }

    @Override
    public long getPollID(String title) {
        Connection con = SQLConnectionProvider.getConnection();
        long id = -1;
        try (PreparedStatement pst = con
                .prepareStatement("SELECT id FROM Polls WHERE title=?")) {
            pst.setString(1, title);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    id = rs.getLong(1);
                }
            }
        } catch (Exception ex) {
            throw new DAOException("Unable to get polls.", ex);
        }
        return id;
    }
}
