// File: src/main/java/com/agroconnect/util/DBConnection.java
package com.agroconnect.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for SQLite connection
 */
public class DBConnection {

    // Path to your SQLite database file
    private static final String DB_URL = "jdbc:sqlite:agroconnect.db";

    /**
     * Returns a Connection to the SQLite database
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("SQLite connected successfully!");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
        return connection;
    }
}
