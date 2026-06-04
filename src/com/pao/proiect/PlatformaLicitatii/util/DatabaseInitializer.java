package com.pao.proiect.PlatformaLicitatii.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try {
            Connection connection =
                    DatabaseConnection.getInstance().getConnection();

            try (Statement pragma = connection.createStatement()) {
                pragma.execute("PRAGMA foreign_keys = ON;");
            }

            StringBuilder sql = new StringBuilder();

            try (BufferedReader reader =
                         new BufferedReader(new FileReader(
                                 "src/com/pao/proiect/PlatformaLicitatii/resources/schema.sql"))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    sql.append(line).append("\n");
                }
            }

            String[] queries = sql.toString().split(";");

            try (Statement statement = connection.createStatement()) {
                for (String query : queries) {
                    String q = query.trim();
                    if (q.isEmpty()) continue;

                    statement.executeUpdate(q);
                }
            }

            System.out.println("Schema SQL executata cu succes!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}