package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String DATABASE_URL = "jdbc:h2:mega_soft_company";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private static final Database INSTANCE = new Database();
    private final Connection connection;

    private Database() {
        //Ініціалізуємо з'єднання з базою даних
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }
}