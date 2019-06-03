package com.pao.project.services;

import com.pao.project.actors.Client;
import com.pao.project.database.DBConnector;
import com.pao.project.manager.FreshManager;
import com.pao.project.manager.ProductCodes;
import com.pao.project.products.Product;
import com.pao.project.side.server.ConnToClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ServerService implements ProductCodes {
    public FreshManager<Client> clientManager;
    public DBConnector connector;
    public ConnToClient client;


    static ServerService instance = null;

    ServerService() {
        connector = new DBConnector();
        connector.init();
        clientManager = new FreshManager<>();
        importClientData();
    }


    public static ServerService getInstance() {
        if(instance == null)
            instance = new ServerService();

        return instance;
    }

    public void initConnection()  {

        int port_number = 7800;
        try {
            ServerSocket serverSocket = new ServerSocket(port_number);
            serverSocket.setSoTimeout(15000); // 15 seconds

            Socket clientSocket = serverSocket.accept();

            client = new ConnToClient(clientSocket);
            client.run();

        } catch (SocketTimeoutException e) {
            e.printStackTrace();

            System.out.println("Connection timed out.");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FreshManager getManager() {
//        switch (manType) {
//            case Product.CLIENT:
//                return clientManager;
//
//            default:
//                return null;
//        }
        return clientManager;
    }

    public void outputManager() {
        clientManager.output();
    }

    public void importClientData() {
        String query = "SELECT * FROM users " +
                "WHERE is_admin = 'NO' ";
        String[] col = {"user_id",
                "username", "password", "email", "telephone"};

        ResultSet clientData = connector.makeQuery(query);

        while (true) {
            try {
                if (!clientData.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();

                System.out.println("ServerService::importDatat\t" +
                        Thread.currentThread().getName());
                System.out.println("Error in result set.");
            }

            try {

                Client client = new Client(
                        clientData.getInt(col[0]),
                        clientData.getString(col[1]),
                        clientData.getString(col[2]),
                        clientData.getString(col[3]),
                        clientData.getString(col[4])
                );

                clientManager.add(client);


            } catch (SQLException e) {
                e.printStackTrace();

                System.out.println("ServerService::importDatat\t" +
                        Thread.currentThread().getName());
                System.out.println("Error while trying to read data from " +
                        "result set.");
            }
        }

        connector.closePrevQuery();
    }


}
