package com.pao.project.database;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.*;

public class DBTest {

//    private DBTest instance = null;

    public static void main(String[] args) {

        String databaseName = "perlafarm_pao";
        String username = "perlafarm";
        String password = "casatefacibine";


        Connection conn = null;
        Statement statem = null;
        ResultSet resSet = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" +
                    "127.0.0.1:3306/" + databaseName +
                    "?useSSL=false", username, password);

            statem = conn.createStatement();

//            String qrySQL = "INSERT INTO users(username, password, " +
//                    "email, telephone, is_admin)" +
//                    " VALUES('username', 'passcalpass', " +
//                    "'a_name@mail.com', '021 123 5477', 'YES')";

//            boolean done = statem.execute(qrySQL);
//            if(done) System.out.println("Linie adaugata cu succes");
//            else System.out.println("Esec in a adauga linia");

            String qrySQL = "SELECT username FROM users" +
                    " WHERE username = 'userename'";
            statem = conn.createStatement();
            resSet = statem.executeQuery(qrySQL);

            while(resSet.next()) {
                System.out.println(resSet.getString("username") );
            }

        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println("Well... thats an error..");
        }   catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != resSet) resSet.close();
                if(null != statem) statem.close();
                if(null != conn) conn.close();
            } catch (SQLException e) {
                System.out.println("Eroare la inchiderea conexiunii");
            }
        }
    }

//    public DBTest getInstance() {
//        if(null == instance) new DBTest();
//        return instance;
//    }
}
