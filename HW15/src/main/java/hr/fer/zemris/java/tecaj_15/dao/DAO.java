package hr.fer.zemris.java.tecaj_15.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_15.model.BlogComment;
import hr.fer.zemris.java.tecaj_15.model.BlogEntry;
import hr.fer.zemris.java.tecaj_15.model.BlogUser;

/**
 * DAO interface represents data access object for blog entries database. It is
 * used for retrieving, creating and updating blog authors, entries and
 * comments.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public interface DAO {
    /**
     * Returns blog entry from database with given id.
     * 
     * @param id Blog entry ID.
     * @return Blog entry from database with given id.
     * @throws DAOException On error with accessing to a database.
     */
    public BlogEntry getBlogEntry(Long id) throws DAOException;

    /**
     * Returns an instance of {@link BlogUser} from users database with given
     * nick.
     * 
     * @param nick Blog user nick.
     * @return Instance of {@link BlogUser} from users database with given nick.
     * @throws DAOException On error with accessing to a database.
     */
    public BlogUser getBlogUser(String nick) throws DAOException;

    /**
     * Lists all registered blog users form blog database.
     * 
     * @return List of all registered blog users.
     * @throws DAOException On error with accessing to a database.
     */
    public List<BlogUser> getAllUsers() throws DAOException;

    /**
     * Adds a new blog user to a database.
     * 
     * @param user New blog user.
     * @throws DAOException On error with accessing to a database.
     */
    public void addBlogUser(BlogUser user) throws DAOException;

    /**
     * Lists all blog posts from given user. User is specified with its nick,
     * nick is unique for each user.
     * 
     * @param nick Unique identifier for each blog user.
     * @return List of all blog posts from given user as list of
     *         {@link BlogEntry} objects.
     * @throws DAOException On error with accessing to a database.
     */
    public List<BlogEntry> getBlogEntries(String nick) throws DAOException;

    /**
     * Updates blog entry in database.
     * 
     * @param entry Changed blog entry.
     * @throws DAOException On error with accessing to a database.
     */
    public void updateBlogEntry(BlogEntry entry) throws DAOException;

    /**
     * Adds a new blog entry in database.
     * 
     * @param entry New blog entry.
     * @throws DAOException On error with accessing to a database.
     */
    public void addBlogEntry(BlogEntry entry) throws DAOException;

    /**
     * Adds a new blog comment in database.
     * 
     * @param comment New blog comment.
     * @throws DAOException On error with accessing to a database.
     */
    public void addComment(BlogComment comment) throws DAOException;
}