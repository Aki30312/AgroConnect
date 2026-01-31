package com.agroconnect.util;

import java.sql.Connection;
import java.sql.Statement;

public class DBInit {

    public static void createTables() {
/*-- Stores registered farmer  account information*/

        String farmers = """
            CREATE TABLE IF NOT EXISTS farmers (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              name TEXT NOT NULL,
              email TEXT UNIQUE NOT NULL,
              password TEXT NOT NULL,
              phone TEXT,
              location TEXT
            );
        """;

        String buyers = """
            CREATE TABLE IF NOT EXISTS buyers (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              name TEXT NOT NULL,
              email TEXT UNIQUE NOT NULL,
              password TEXT NOT NULL,
              phone TEXT
            );
        """;

        String products = """
            CREATE TABLE IF NOT EXISTS products (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              farmer_id INTEGER NOT NULL,
              name TEXT NOT NULL,
              price REAL NOT NULL,
              quantity INTEGER NOT NULL,
              FOREIGN KEY (farmer_id) REFERENCES farmers(id)
            );
        """;

        String orders = """
            CREATE TABLE IF NOT EXISTS orders (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              buyer_id INTEGER NOT NULL,
              product_id INTEGER NOT NULL,
              quantity INTEGER NOT NULL,
              order_date TEXT DEFAULT (datetime('now')),
              FOREIGN KEY (buyer_id) REFERENCES buyers(id),
              FOREIGN KEY (product_id) REFERENCES products(id)
            );
        """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Create tables (safe)
            stmt.execute(farmers);
            stmt.execute(buyers);
            stmt.execute(products);
            stmt.execute(orders);

            // ---- MIGRATIONS (keeps old DB and upgrades it) ----
            // If the farmers table was created earlier without these columns, add them now.
            try { stmt.execute("ALTER TABLE farmers ADD COLUMN phone TEXT"); } catch (Exception ignored) {}
            try { stmt.execute("ALTER TABLE farmers ADD COLUMN location TEXT"); } catch (Exception ignored) {}

            // If buyers table was created earlier without phone
            try { stmt.execute("ALTER TABLE buyers ADD COLUMN phone TEXT"); } catch (Exception ignored) {}

            System.out.println("Tables created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}