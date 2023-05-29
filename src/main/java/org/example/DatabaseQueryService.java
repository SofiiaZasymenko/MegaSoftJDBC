package org.example;

import org.example.models.Level;
import org.example.models.LongestProject;
import org.example.models.MaxProjectsClient;
import org.example.models.MaxSalaryWorker;
import org.example.models.ProjectPrices;
import org.example.models.Worker;
import org.example.models.YoungestEldestWorkers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private static final String SELECT_WORKER_BY_NAME_STRING =
            "SELECT id, name, birthday, level, salary FROM worker WHERE name = ?";

    public static void main(String[] args) {
        DatabaseQueryService service = new DatabaseQueryService();
        System.out.println("---WorkerByName---");
        System.out.println(service.findWorkerByName("Jack"));
        System.out.println(service.findWorkerByName("Jane"));
        System.out.println("---LongestProject---");
        System.out.println(service.findLongestProject());
        System.out.println("---MaxProjectsClient---");
        System.out.println(service.findMaxProjectsClient());
        System.out.println("---MaxSalaryWorker---");
        System.out.println(service.findMaxSalaryWorker());
        System.out.println("---YoungestEldestWorkers---");
        System.out.println(service.findYoungestEldestWorkers());
        System.out.println("---ProjectPrices---");
        System.out.println(service.printProjectPrices());
    }

    public List<Worker> findWorkerByName(String name) {
        List<Worker> result = new ArrayList<>();
        Connection conn = Database.getInstance().getConnection();
        try (PreparedStatement selectWorkerStmt = conn.prepareStatement(SELECT_WORKER_BY_NAME_STRING)) {
            selectWorkerStmt.setString(1, name);
            ResultSet resultSet = selectWorkerStmt.executeQuery();
            while (resultSet.next()) {
                result.add(new Worker(resultSet.getInt(1), resultSet.getString(2),
                        LocalDate.parse(resultSet.getString(3)), Level.fromName(resultSet.getString(4)),
                        resultSet.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<LongestProject> findLongestProject() {
        List<LongestProject> result = new ArrayList<>();
        String query = QueryReader.readQueryFromFile("sql/find_longest_project.sql");
        Connection conn = Database.getInstance().getConnection();
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                result.add(new LongestProject(resultSet.getInt(1), resultSet.getInt(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<MaxProjectsClient> findMaxProjectsClient() {
        List<MaxProjectsClient> result = new ArrayList<>();
        String query = QueryReader.readQueryFromFile("sql/find_max_projects_client.sql");
        Connection conn = Database.getInstance().getConnection();
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                result.add(new MaxProjectsClient(resultSet.getString(1), resultSet.getInt(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<MaxSalaryWorker> findMaxSalaryWorker() {
        List<MaxSalaryWorker> result = new ArrayList<>();
        String query = QueryReader.readQueryFromFile("sql/find_max_salary_worker.sql");
        Connection conn = Database.getInstance().getConnection();
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                result.add(new MaxSalaryWorker(resultSet.getString(1), resultSet.getInt(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<YoungestEldestWorkers> findYoungestEldestWorkers() {
        List<YoungestEldestWorkers> result = new ArrayList<>();
        String query = QueryReader.readQueryFromFile("sql/find_youngest_eldest_workers.sql");
        Connection conn = Database.getInstance().getConnection();
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                result.add(new YoungestEldestWorkers(resultSet.getString(1), resultSet.getString(2), LocalDate.parse(resultSet.getString(3))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<ProjectPrices> printProjectPrices() {
        List<ProjectPrices> result = new ArrayList<>();
        String query = QueryReader.readQueryFromFile("sql/print_project_prices.sql");
        Connection conn = Database.getInstance().getConnection();
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                result.add(new ProjectPrices(resultSet.getString(1), resultSet.getInt(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}