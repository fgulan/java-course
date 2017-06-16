package hr.fer.zemris.java.hw14.dao.sql;

import hr.fer.zemris.java.hw14.dao.DAO;

/**
 * DAOProvider class, using singleton design pattern, returns currently
 * supported {@link DAO} object for current database format.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class DAOProvider {

    /** Currenty supported DAO. */
    private static DAO dao = new SQLDao();

    /**
     * Returns currently supported {@link DAO} object for accessing a current
     * database system.
     * 
     * @return {@link DAO} object.
     */
    public static DAO getDao() {
        return dao;
    }

}