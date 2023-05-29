package org.example;

import org.example.models.Client;
import org.example.models.Level;
import org.example.models.Project;
import org.example.models.ProjectWorker;
import org.example.models.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

public class DatabasePopulateService {

    private static final String INSERT_WORKER_STRING = "INSERT INTO worker (id, name, birthday, level, salary) VALUES " +
            "(?, ?, ?, ?, ?);";
    private static final String INSERT_CLIENT_STRING = "INSERT INTO client (id, name) VALUES (?, ?);";
    private static final String INSERT_PROJECT_STRING = "INSERT INTO project (id, client_id, start_date, finish_date) " +
            "VALUES (?, ?, ?, ?);";
    private static final String INSERT_PROJECT_WORKER_STRING = "INSERT INTO project_worker (project_id, worker_id) " +
            "VALUES (?, ?);";

    private static final Set<Worker> WORKERS = Set.of(
            new Worker(1, "Nick", LocalDate.parse("2000-01-01"), Level.JUNIOR, 500),
            new Worker(2, "Max", LocalDate.parse("1998-11-30"), Level.JUNIOR, 750),
            new Worker(3, "Jack", LocalDate.parse("1991-03-18"), Level.MIDDLE, 2500),
            new Worker(4, "Orlando", LocalDate.parse("1977-01-13"), Level.SENIOR, 10000),
            new Worker(5, "Mary", LocalDate.parse("1998-10-13"), Level.MIDDLE, 5000),
            new Worker(6, "Sam", LocalDate.parse("1986-09-23"), Level.MIDDLE, 6500),
            new Worker(7, "Jane", LocalDate.parse("2001-08-15"), Level.TRAINEE, 350),
            new Worker(8, "Michael", LocalDate.parse("2003-04-09"), Level.TRAINEE, 200),
            new Worker(9, "Taylor", LocalDate.parse("1989-12-13"), Level.SENIOR, 7500),
            new Worker(10, "Jennifer", LocalDate.parse("1969-07-24"), Level.SENIOR, 8500),
            new Worker(11, "Miranda", LocalDate.parse("2003-04-09"), Level.JUNIOR, 200)
    );

    private static final Set<Client> CLIENTS = Set.of(
            new Client(1, "Ann"),
            new Client(2, "Betty"),
            new Client(3, "Ken"),
            new Client(4, "Robin"),
            new Client(5, "Bruce")
    );

    private static final Set<Project> PROJECTS = Set.of(
            new Project(1, 3, LocalDate.parse("2020-01-01"), LocalDate.parse("2023-10-30")),
            new Project(2, 5, LocalDate.parse("2022-03-05"), LocalDate.parse("2024-01-10")),
            new Project(3, 1, LocalDate.parse("2021-02-24"), LocalDate.parse("2024-03-09")),
            new Project(4, 2, LocalDate.parse("2020-04-01"), LocalDate.parse("2023-08-20")),
            new Project(5, 5, LocalDate.parse("2019-08-25"), LocalDate.parse("2023-09-24")),
            new Project(6, 1, LocalDate.parse("2022-07-19"), LocalDate.parse("2024-02-24")),
            new Project(7, 2, LocalDate.parse("2023-01-19"), LocalDate.parse("2024-04-29")),
            new Project(8, 5, LocalDate.parse("2018-09-30"), LocalDate.parse("2023-10-10")),
            new Project(9, 1, LocalDate.parse("2019-11-22"), LocalDate.parse("2023-11-25")),
            new Project(10, 4, LocalDate.parse("2021-12-05"), LocalDate.parse("2024-01-30"))
    );

    private static final Set<ProjectWorker> PROJECT_WORKERS = Set.of(
            new ProjectWorker(1, 5),
            new ProjectWorker(1, 8),
            new ProjectWorker(2, 10),
            new ProjectWorker(3, 1),
            new ProjectWorker(3, 2),
            new ProjectWorker(3, 6),
            new ProjectWorker(3, 7),
            new ProjectWorker(4, 3),
            new ProjectWorker(4, 4),
            new ProjectWorker(4, 9),
            new ProjectWorker(5, 7),
            new ProjectWorker(6, 2),
            new ProjectWorker(6, 4),
            new ProjectWorker(6, 6),
            new ProjectWorker(6, 8),
            new ProjectWorker(6, 10),
            new ProjectWorker(7, 1),
            new ProjectWorker(7, 3),
            new ProjectWorker(7, 5),
            new ProjectWorker(8, 2),
            new ProjectWorker(8, 7),
            new ProjectWorker(9, 4),
            new ProjectWorker(9, 9),
            new ProjectWorker(10, 5),
            new ProjectWorker(10, 10)
    );

    public static void main(String[] args) {
        Connection conn = Database.getInstance().getConnection();
        try (PreparedStatement insertWorkerStmt = conn.prepareStatement(INSERT_WORKER_STRING);
             PreparedStatement insertClientStmt = conn.prepareStatement(INSERT_CLIENT_STRING);
             PreparedStatement insertProjectStmt = conn.prepareStatement(INSERT_PROJECT_STRING);
             PreparedStatement insertProjectWorkerStmt = conn.prepareStatement(INSERT_PROJECT_WORKER_STRING)) {
            //Insert WORKERS
            for (Worker worker : WORKERS) {
                addWorkerBatch(insertWorkerStmt, worker);
            }
            int[] insertedRows = insertWorkerStmt.executeBatch();
            System.out.println("Inserted workers: " + getNumberOfSuccessfulRows(insertedRows));
            //Insert CLIENTS
            for (Client client : CLIENTS) {
                addClientBatch(insertClientStmt, client);
            }
            insertedRows = insertClientStmt.executeBatch();
            System.out.println("Inserted clients: " + getNumberOfSuccessfulRows(insertedRows));
            //Insert PROJECTS
            for (Project project : PROJECTS) {
                addProjectBatch(insertProjectStmt, project);
            }
            insertedRows = insertProjectStmt.executeBatch();
            System.out.println("Inserted projects: " + getNumberOfSuccessfulRows(insertedRows));
            //Insert PROJECT_WORKERS
            for (ProjectWorker projectWorker : PROJECT_WORKERS) {
                addProjectWorkerBatch(insertProjectWorkerStmt, projectWorker);
            }
            insertedRows = insertProjectWorkerStmt.executeBatch();
            System.out.println("Inserted project workers: " + getNumberOfSuccessfulRows(insertedRows));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addWorkerBatch(PreparedStatement insertStmt, Worker worker) throws SQLException {
        insertStmt.setInt(1, worker.getId());
        insertStmt.setString(2, worker.getName());
        insertStmt.setDate(3, java.sql.Date.valueOf(worker.getBirthday().toString()));
        insertStmt.setString(4, worker.getLevel().toString());
        insertStmt.setInt(5, worker.getSalary());
        insertStmt.addBatch();
    }

    private static void addClientBatch(PreparedStatement insertStmt, Client client) throws SQLException {
        insertStmt.setInt(1, client.getId());
        insertStmt.setString(2, client.getName());
        insertStmt.addBatch();
    }

    private static void addProjectBatch(PreparedStatement insertStmt, Project project) throws SQLException {
        insertStmt.setInt(1, project.getId());
        insertStmt.setInt(2, project.getClientId());
        insertStmt.setDate(3, java.sql.Date.valueOf(project.getStartDate().toString()));
        insertStmt.setDate(4, java.sql.Date.valueOf(project.getFinishDate().toString()));
        insertStmt.addBatch();
    }

    private static void addProjectWorkerBatch(PreparedStatement insertStmt, ProjectWorker projectWorker) throws SQLException {
        insertStmt.setInt(1, projectWorker.getProjectId());
        insertStmt.setInt(2, projectWorker.getWorkerId());
        insertStmt.addBatch();
    }

    private static long getNumberOfSuccessfulRows(int[] insertedRows) {
        return Arrays.stream(insertedRows)
                .filter(status -> status == 1)
                .count();
    }
}