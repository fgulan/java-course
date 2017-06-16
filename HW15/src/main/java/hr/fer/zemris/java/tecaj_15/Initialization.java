package hr.fer.zemris.java.tecaj_15;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.tecaj_15.dao.jpa.JPAEMFProvider;

/**
 * Initialization class implements {@link ServletContextListener} interface. On
 * web application startup it creates an {@link EntityManagerFactory} instance
 * for accessing a database with blog entries using Hibernate. On closing an
 * application it closes all created entity managers.
 * 
 * @author Filip
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("baza.podataka.za.blog");
        sce.getServletContext().setAttribute("my.application.emf", emf);
        JPAEMFProvider.setEmf(emf);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAEMFProvider.setEmf(null);
        EntityManagerFactory emf = (EntityManagerFactory) sce
                .getServletContext().getAttribute("my.application.emf");
        if (emf != null) {
            emf.close();
        }
    }
}