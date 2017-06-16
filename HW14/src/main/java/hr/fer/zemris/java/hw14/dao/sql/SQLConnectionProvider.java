package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * SQLConnectionProvider class, using singleton pattern, establish connection
 * with database for current thread.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class SQLConnectionProvider {

    /** Current connections. */
    private static ThreadLocal<Connection> connections = new ThreadLocal<>();

    /**
     * Sets a connection for current thread, or deletes it form map if parameter
     * is null.
     * 
     * @param con Database connection.
     */
    public static void setConnection(Connection con) {
        if (con == null) {
            connections.remove();
        } else {
            connections.set(con);
        }
    }

    /**
     * Returns current connection available for current thread.
     * 
     * @return Current connection available for current thread.
     */
    public static Connection getConnection() {
        return connections.get();
    }

}