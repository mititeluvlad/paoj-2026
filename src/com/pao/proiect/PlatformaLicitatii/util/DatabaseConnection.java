package com.pao.proiect.PlatformaLicitatii.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            Properties properties = new Properties();

            FileInputStream fis = new FileInputStream("src/com/pao/proiect/PlatformaLicitatii/resources/db.properties");

            properties.load(fis);

            String url = properties.getProperty("db.url");

            connection = DriverManager.getConnection(url);
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
            System.out.println("Conexiune SQLite realizata!");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}