package com.pitroq.kevin;

import java.sql.*;

public class Database {
    private Connection connect = null;

    public Database connect() throws SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection("jdbc:mysql://localhost/kevin", "root", "");
        return this;
    }

    public void sendQuery(String query) throws SQLException {
        Statement statement = connect.createStatement();
        statement.execute(query);
    }

    public void close() {
        if (connect == null) return;

        try {
            connect.close();
        }
        catch (SQLException ignored) {}
    }
}