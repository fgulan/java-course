package hr.fer.zemris.java.tecaj_15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * JPAEMFProvider class is used as provider for currently supported instance of
 * {@link EntityManagerFactory} object.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class JPAEMFProvider {

    /** Current entity manager factory. */
    public static EntityManagerFactory emf;

    /**
     * Returns current entity manager factory.
     * 
     * @return Current entity manager factory.
     */
    public static EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * Sets a new entity manager factory.
     * 
     * @param emf New entity manager factory.
     */
    public static void setEmf(EntityManagerFactory emf) {
        JPAEMFProvider.emf = emf;
    }
}