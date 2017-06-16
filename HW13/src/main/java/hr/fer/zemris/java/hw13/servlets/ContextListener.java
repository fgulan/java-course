package hr.fer.zemris.java.hw13.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * ContextListener class implements {@link ServletContextListener} interface. It is used for
 * initializing start up time of servlet.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            context.setAttribute("upTime", System.currentTimeMillis());
        } catch (Exception e) {
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            context.removeAttribute("upTime");
        } catch (Exception e) {
        }
    }
}
