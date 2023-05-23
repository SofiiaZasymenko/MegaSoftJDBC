package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DatabaseInitService {
    public static void main(String[] args) {
        List<String> queries = QueryReader.readAllQueriesFromFile("sql/init_db.sql");
        Connection conn = Database.getInstance().getConnection();
        try (Statement stmt = conn.createStatement()) {
            for (String query : queries) {
                stmt.execute(query);
            }
            ResultSet resultSet = stmt.executeQuery("SHOW TABLES");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}