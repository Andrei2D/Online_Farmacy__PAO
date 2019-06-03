package com.pao.project.side.server;

import com.pao.project.actors.Client;
import com.pao.project.actors.User;
import com.pao.project.connection.req.Req;
import com.pao.project.connection.req.ReqConstants;
import com.pao.project.database.DBConnector;
import com.pao.project.manager.ProductCodes;
import com.pao.project.services.ServerService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnToClient extends  Thread implements ReqConstants {
    private volatile boolean sessionActive;
    private Socket clientSocket;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    public ConnToClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
        sessionActive = true;

        try {
            inFromClient = new ObjectInputStream(
                    clientSocket.getInputStream()
            );

            outToClient = new ObjectOutputStream(
                    clientSocket.getOutputStream()
            );
        } catch (IOException e) {
            System.out.println("ConnToClient::ConnToClient\t" +
                    Thread.currentThread().getName());
            System.out.println("Cross pairing failed server side.");
            sessionActive = false;
            stopConn();
        }

    }

    boolean isActive() {
        return sessionActive;
    }

    @Override
    public void run() {
        while(sessionActive) {
            Req newReq = getRequest();
            switch (newReq.getReqValue()) {
                case HLO:
                    quickMessage(HLO);
                    break;

                case BYE:
                    quickMessage(BYE);
                    stopConn();
                    break;

                case LIR:
                    logInRequest();
                    break;

                case CAR:
                    createAccReq();
                    break;

                default:
                    quickMessage(NK);
                    break;
            }
        }
    }

    private void logInRequest() {
        Req send_req = new Req(SOR);

        try {
            outToClient.writeObject(send_req);

        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("ConnToClient::logInRequest\t" +
                    Thread.currentThread().getName());
            System.out.println("Error while sending SOR mesage to client." );
        }

        User user = new User();

        try {

            user = (User)inFromClient.readObject();
            System.out.println("User that requested log in:");
            System.out.println(user);

        }  catch (IOException e) {
            e.printStackTrace();

            System.out.println("ConnToClient::logInRequest\t" +
                    Thread.currentThread().getName());
            System.out.println("Failed reading class message from client.");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

            System.out.println("ConnToClient::logInRequest\t" +
                    Thread.currentThread().getName());
            System.out.println("Error at finding User class type.");
        }

        User existingUser = (User) ServerService.getInstance().getManager().
                get(user.getUsername());

        if(null == existingUser) {
            System.out.println("Existing user... NULl");
            quickMessage(NK);
            return;
        }
        if(existingUser.getPassword().equals(user.getPassword()))
            quickMessage(OK);
    }

    private void createAccReq() {
        Req send_req = new Req(SOR);
        System.out.println("Got in create acc req");
        try {
            outToClient.writeObject(send_req);

        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("ConnToClient::createAccReq\t" +
                    Thread.currentThread().getName());
            System.out.println("Error while sending SOR mesage to client." );
        }

        Client client = null;

        try {

            client = (Client)inFromClient.readObject();
            System.out.println("Succesfully received client:");
            System.out.println(client);

        }  catch (IOException e) {
            e.printStackTrace();

            System.out.println("ConnToClient::createAccReq\t" +
                    Thread.currentThread().getName());
            System.out.println("Failed reading class message from client.");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

            System.out.println("ConnToClient::createAccReq\t" +
                    Thread.currentThread().getName());
            System.out.println("Error at finding User class type.");
        }

        if(checkNewAccViability(client))  quickMessage(OK);
        else quickMessage(NK);
    }

    private Req getRequest() {
        Req newRequestMessage = new Req();

        try {
            newRequestMessage = (Req)inFromClient.readObject();
        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("ConnToClient::getRequest\t" +
                    Thread.currentThread().getName());
            System.out.println("Failed reading message from client.");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

            System.out.println("ConnToClient::getRequest\t" +
                    Thread.currentThread().getName());
            System.out.println("Error at finding the class type.");
        }

        return newRequestMessage;
    }

    boolean checkNewAccViability(Client client) {
        if(client == null) return false;
        String query = "SELECT * FROM users " +
                "WHERE username = '" + client.getUsername() + "' OR " +
                "email = '" + client.getEmail() + "' ";
        ResultSet resultSet = ServerService.getInstance()
                .connector.makeQuery(query);
        String[] col = {"user_id",
                "username", "password", "email", "telephone"};

        try {
            boolean boolToTest = resultSet.next();
            System.out.println("BOOL THAT GOT TESTED " + boolToTest);
            if(boolToTest)
                return false;
            else {
                quickMessage(OK);
                String addQuery = "INSERT INTO " +
                        "'users' ('username', 'password', 'is_admin', " +
                        "'email', 'telephone') VALUES " +
                        "('" + client.getUsername() + "', '" +
                        client.getPassword() + "', 'NO', '" +
                        client.getEmail() + "', '" +
                        client.getTelephone() + "');\n";

                ServerService.getInstance().connector
                        .insertQuery(addQuery);

                ResultSet newlyAdded = ServerService.getInstance().connector
                        .makeQuery(query);

                ServerService.getInstance().getManager().add(
                        new Client(
                                resultSet.getInt(col[0]),
                                resultSet.getString(col[1]),
                                resultSet.getString(col[2]),
                                resultSet.getString(col[3]),
                                resultSet.getString(col[4])
                        )
                );
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Username or email already existing.");
            return false;
        }
    }

    private void quickMessage (String mess) {
        try {

            Req quick = new Req(mess);
            outToClient.writeObject(quick);

        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("ConnToClient::quickMessage\t"
                    + Thread.currentThread().getName());
            System.out.println("Failed sending " +
                    mess.toUpperCase() + " message to client.");
        }
    }


    void stopConn() {
        try {

            sessionActive = false;
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("ConnToClient::stop\t" +
                    Thread.currentThread().getName());
            System.out.println("Failed at closing the socket.");
        }
    }
}

