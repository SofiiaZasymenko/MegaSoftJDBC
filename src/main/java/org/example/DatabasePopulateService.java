package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DatabasePopulateService {
    public static void main(String[] args) {
        List<String> queries = QueryReader.readAllQueriesFromFile("sql/populate_db.sql");
        Connection conn = Database.getInstance().getConnection();
        try (Statement stmt = conn.createStatement()) {
            for (String query : queries) {
                int updatedRecords = stmt.executeUpdate(query);
                System.out.println(updatedRecords);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}