// File: src/main/java/com/agriconnect/util/DBConnection.java
package com.agroconnect.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for SQLite connection
 */
public class DBConnection {

    // Recommended: store DB inside project folder (same directory you run from)
    private static final String DB_URL = "jdbc:sqlite:agroconnect.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}