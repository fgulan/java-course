package hr.fer.zemris.java.tecaj_15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_15.dao.DAOException;

/**
 * JPAEMProvider class is used as provider for currently supported instance of
 * {@link EntityManager} object.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class JPAEMProvider {

    /** Current thread local data. */
    private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

    /**
     * Returns a new {@link EntityManager} instance for current thread.
     * 
     * @return A new {@link EntityManager} instance for current thread.
     */
    public static EntityManager getEntityManager() {
        LocalData ldata = locals.get();
        if (ldata == null) {
            ldata = new LocalData();
            ldata.em = JPAEMFProvider.getEmf().createEntityManager();
            ldata.em.getTransaction().begin();
            locals.set(ldata);
        }
        return ldata.em;
    }

    /**
     * Commits current transaction and closes current entity manager instance.
     * 
     * @throws DAOException On error with executing a transaction.
     */
    public static void close() throws DAOException {
        LocalData ldata = locals.get();
        if (ldata == null) {
            return;
        }
        DAOException dex = null;
        try {
            ldata.em.getTransaction().commit();
        } catch (Exception ex) {
            dex = new DAOException("Unable to commit transaction.", ex);
        }
        try {
            ldata.em.close();
        } catch (Exception ex) {
            if (dex != null) {
                dex = new DAOException("Unable to close entity manager.", ex);
            }
        }
        locals.remove();
        if (dex != null) {
            throw dex;
        }
    }

    /**
     * LocalData class represents a local data for each thread. In this case
     * that is an {@link EntityManager} instance.
     * 
     * @author Filip
     * @version 1.0
     *
     */
    private static class LocalData {
        /** Entity manager. */
        EntityManager em;
    }

}