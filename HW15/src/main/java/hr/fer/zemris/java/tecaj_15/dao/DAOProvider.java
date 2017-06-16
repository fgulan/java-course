package hr.fer.zemris.java.tecaj_15.dao;

import hr.fer.zemris.java.tecaj_15.dao.jpa.JPADAOImpl;

/**
 * DAOProvider class, using singleton design pattern, returns currently
 * supported {@link DAO} object for current database format.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class DAOProvider {
    /** Currently supported DAO. */
    private static DAO dao = new JPADAOImpl();

    /**
     * Returns currently supported {@link DAO} object for accessing a current
     * database system.
     * 
     * @return {@link DAO} object.
     */
    public static DAO getDAO() {
        return dao;
    }
}