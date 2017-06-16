package hr.fer.zemris.java.hw14;

import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * ContextListener class implements {@link ServletContextListener} interface. It
 * is used for initializing database upon starting a web application.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComboPooledDataSource cpds = (ComboPooledDataSource) sce
                .getServletContext().getAttribute("hr.fer.zemris.dbpool");
        if (cpds != null) {
            try {
                DataSources.destroy(cpds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // LOAD CONFIGURATION
        Properties dbConfig = new Properties();
        String fileName = sce.getServletContext().getRealPath(
                "/WEB-INF/dbsettings.properties");
        try {
            dbConfig.load(Files.newInputStream(Paths.get(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read database configuration files!");
        }

        String dbName = dbConfig.getProperty("name");
        String dbHost = dbConfig.getProperty("host");
        String dbPort = dbConfig.getProperty("port");
        String dbUser = dbConfig.getProperty("user");
        String dbPassword = dbConfig.getProperty("password");

        String connectionURL = "jdbc:derby://" + dbHost + ":" + dbPort + "/"
                + dbName + ";user=" + dbUser + ";password=" + dbPassword
                + ";create=true";

        // CREATE CONNECTION
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
        } catch (PropertyVetoException e1) {
            throw new RuntimeException("Unable to start an aplication.", e1);
        }
        cpds.setJdbcUrl(connectionURL);

        // FILL TABLES
        Connection con = null;
        try {
            con = cpds.getConnection();
            SQLConnectionProvider.setConnection(con);
            if (!DBUtility.isTablePresent("POLLS", con)) {
                DBUtility.createPollsTable(con);
            }
            if (!DBUtility.isTablePresent("PollOptions", con)) {
                DBUtility.createPollOptionsTable(con);
            }
            DBUtility.fillGuitarTable(sce.getServletContext().getRealPath(
                    "/WEB-INF/guitar.txt"));
            DBUtility.fillBandTable(sce.getServletContext().getRealPath(
                    "/WEB-INF/band.txt"));
            con.close();
        } catch (SQLException e) {
        }
        sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
    }
}
