package hr.fer.zemris.java.tecaj_15.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_15.dao.DAO;
import hr.fer.zemris.java.tecaj_15.dao.DAOException;
import hr.fer.zemris.java.tecaj_15.model.BlogComment;
import hr.fer.zemris.java.tecaj_15.model.BlogEntry;
import hr.fer.zemris.java.tecaj_15.model.BlogUser;

/**
 * JPADAOImpl implements {@link DAO} interface. JPADAOImpl represents data
 * access object used in relation database system with Java Persistence API and
 * Hibrenate . In current case It is implemented for retrieving, creating and
 * updating blog authors, entries and comments.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class JPADAOImpl implements DAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public BlogEntry getBlogEntry(Long id) throws DAOException {
        BlogEntry blogEntry = JPAEMProvider.getEntityManager()
                .find(BlogEntry.class, id);
        return blogEntry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlogUser getBlogUser(String nick) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();
        @SuppressWarnings("unchecked")
        List<BlogUser> user = (List<BlogUser>) em
                .createQuery("select b from BlogUser as b where b.nick=:nick")
                .setParameter("nick", nick).getResultList();
        return user.isEmpty() ? null : user.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BlogUser> getAllUsers() throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();

        @SuppressWarnings("unchecked")
        List<BlogUser> users = (List<BlogUser>) em.createQuery("from BlogUser")
                .getResultList();
        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBlogUser(BlogUser user) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();
        em.persist(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BlogEntry> getBlogEntries(String nick) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();
        BlogUser user = getBlogUser(nick);
        if (user == null) {
            return new ArrayList<BlogEntry>();
        }

        @SuppressWarnings("unchecked")
        List<BlogEntry> entries = (List<BlogEntry>) em
                .createQuery(
                        "select b from BlogEntry as b where b.creator=:creator")
                .setParameter("creator", user).getResultList();

        return entries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBlogEntry(BlogEntry entry) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();
        em.persist(entry);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBlogEntry(BlogEntry entry) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();
        em.merge(entry);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addComment(BlogComment comment) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();
        em.persist(comment);
    }

}
