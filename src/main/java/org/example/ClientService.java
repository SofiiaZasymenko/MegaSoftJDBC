package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private static final String INSERT_NEW_CLIENT_STRING = "INSERT INTO client (name) VALUES (?);";
    private static final String SELECT_CLIENT_BY_ID_STRING = "SELECT id, name FROM client WHERE id = ?;";
    private static final String UPDATE_NAME_STRING = "UPDATE client SET name = ? WHERE id = ?;";
    private static final String DELETE_CLIENT_STRING = "DELETE FROM client WHERE id = ?;";
    private static final String SELECT_ALL_CLIENTS_STRING = "SELECT id, name FROM client;";

    private PreparedStatement insertStatement;
    private PreparedStatement selectByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private Statement selectAllStatement;

    public ClientService(Connection connection) {
        try {
            this.insertStatement = connection.prepareStatement(INSERT_NEW_CLIENT_STRING, Statement.RETURN_GENERATED_KEYS);
            this.selectByIdStatement = connection.prepareStatement(SELECT_CLIENT_BY_ID_STRING);
            this.updateStatement = connection.prepareStatement(UPDATE_NAME_STRING);
            this.deleteStatement = connection.prepareStatement(DELETE_CLIENT_STRING);
            this.selectAllStatement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Client Service construction exception. Reason: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ClientService clientService = new ClientService(Database.getInstance().getConnection());
        System.out.println("---Insert new client---");
        System.out.println(clientService.create("George"));
        System.out.println(clientService.create("Megan"));
        System.out.println(clientService.create("William"));

        System.out.println("---Select client by id---");
        System.out.println(clientService.getById(8));

        System.out.println("---Update client name---");
        clientService.setName(7, "Kate");
        System.out.println(clientService.getById(7));

        System.out.println("---Delete client---");
        clientService.deleteById(6);
        System.out.println(clientService.getById(6));

        System.out.println("---Select all clients---");
        System.out.println(clientService.listAll());

        System.out.println("---Update invalid client name---");
        try {
            clientService.setName(7, "   ");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(clientService.getById(7));
    }

    public long create(String name) {
        validateClientName(name);
        try {
            insertStatement.setString(1, name);
            int insertResult = insertStatement.executeUpdate();
            if (insertResult == 1) {
                ResultSet resultSet = insertStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new RuntimeException("Result Set is empty!");
            }
            throw new RuntimeException("Client is not created!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getById(long id) {
        try {
            selectByIdStatement.setLong(1, id);
            ResultSet resultSet = selectByIdStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setName(long id, String name) {
        validateClientName(name);
        try {
            updateStatement.setString(1, name);
            updateStatement.setLong(2, id);
            int updateResult = updateStatement.executeUpdate();
            if (updateResult != 1) {
                throw new RuntimeException(String.format("The name for the client (%s) is not update!", id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(long id) {
        try {
            deleteStatement.setLong(1, id);
            int deleteResult = deleteStatement.executeUpdate();
            if (deleteResult != 1) {
                throw new RuntimeException(String.format("The client (%s) is not deleted!", id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> listAll() {
        List<Client> clients = new ArrayList<>();
        try (ResultSet resultSet = this.selectAllStatement.executeQuery(SELECT_ALL_CLIENTS_STRING)) {
            while (resultSet.next()) {
                Client client = new Client(resultSet.getLong("id"),
                        resultSet.getString("name"));
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Select all clients exception. Reason: " + e.getMessage());
        }
        return clients;
    }

    private void validateClientName(String name) {
        if (name == null || name.isBlank() || name.length() > 1000) {
            throw new RuntimeException(String.format("Client name is not valid: '%s'", name));
        }
    }
}