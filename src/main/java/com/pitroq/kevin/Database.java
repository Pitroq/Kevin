package com.pitroq.kevin;

import java.sql.*;

public class Database {
    private Connection connect = null;
    private final Config config = new Config();
    private final String URL = "jdbc:mysql://" + config.get("databaseHost") + "/"+ config.get("databaseName");

    public Database connect() throws SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(URL, config.get("databaseUser"), config.get("databasePassword"));
        return this;
    }

    public void sendQuery(String query) throws SQLException {
        Statement statement = connect.createStatement();
        statement.execute(query);
        statement.close();
    }

    public void close() {
        if (connect == null) return;

        try {
            connect.close();
        }
        catch (SQLException ignored) {}
    }

    public ResultSet sendQueryWithResult(String query) throws SQLException {
        Statement statement = connect.createStatement();
        return statement.executeQuery(query);
    }
}