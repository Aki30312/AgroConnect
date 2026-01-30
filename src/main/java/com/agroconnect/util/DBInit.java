package com.agroconnect.util;

import java.sql.Connection;
import java.sql.Statement;

public class DBInit {

    public static void createTables() {
        String farmers = """
            CREATE TABLE IF NOT EXISTS farmers (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              name TEXT NOT NULL,
              email TEXT UNIQUE NOT NULL,
              password TEXT NOT NULL
     
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
       /* String buyers= """
             CREATE TABLE buyers (
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               name TEXT NOT NULL,
               email TEXT UNIQUE NOT NULL,
               password TEXT NOT NULL,
               phone TEXT
                        );
                
                """;

       String orders= """
          CREATE TABLE orders (
                               id INTEGER PRIMARY KEY AUTOINCREMENT,
                               buyer_id INTEGER NOT NULL,
                               product_id INTEGER NOT NULL,
                               quantity INTEGER NOT NULL,
                               order_date TEXT DEFAULT (datetime('now')),
                               FOREIGN KEY (buyer_id) REFERENCES buyers(id),
                               FOREIGN KEY (product_id) REFERENCES products(id)
                       );
               """;*/
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(farmers);
            stmt.execute(products);

            System.out.println("Tables created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}