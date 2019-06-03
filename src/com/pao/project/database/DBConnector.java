package com.pao.project.database;

import com.pao.project.actors.User;

import java.sql.*;

public class DBConnector {
    private String databaseName = "perlafarm_pao";
    private String username = "perlafarm";
    private String password = "casatefacibine";

    private Connection conn = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public boolean init() {
        try {

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://" +
                "127.0.0.1:3306/" + databaseName +
                "?useSSL=false", username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isOpen() {
        try {
            return !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean close() {
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet makeQuery(String query) {
        closePrevQuery();

        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public void insertQuery(String inserQuery) {
        closePrevQuery();
        try{
            statement = conn.createStatement();
            statement.executeUpdate(inserQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void closePrevQuery() {
        try {

            if (null != resultSet)
                resultSet.close();
            if (null != statement)
                statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DBConnector::closePrevQuery\t" +
                    Thread.currentThread().getName() );
            System.out.println("Error at closing resultset and statement.");
        }
    }



}
