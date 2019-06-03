package com.pao.project.side.client;


import com.pao.project.actors.User;
import com.pao.project.connection.req.Req;
import com.pao.project.connection.req.ReqConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnToServer implements ReqConstants {
    private volatile boolean sessionActive;
    private Socket serverSocket;
    private ObjectInputStream inFromServer;
    private ObjectOutputStream outToServer;

    public ConnToServer(Socket serverSocket) {
        this.serverSocket = serverSocket;
        sessionActive = true;

        try {
            outToServer = new ObjectOutputStream(
                    serverSocket.getOutputStream()
            );

            inFromServer = new ObjectInputStream(
                    serverSocket.getInputStream()
            );
        } catch (IOException e) {
            System.out.println("ConnToServer::init\t" +
                    Thread.currentThread().getName());
            System.out.println("Cross pairing failed on client side.");
            sessionActive = false;
            stopConn();
        }

        // Initial handshake
        quickMessage(HLO);
        Req backHello = new Req();
        try {
            backHello = (Req)inFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ConnToServer::ConnToServer\t" +
                    Thread.currentThread().getName());
            System.out.println("Error at receiving a message from server, " +
                    "namely a Req object.");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

            System.out.println("ConnToServer::ConnToServer\t" +
                    Thread.currentThread().getName());
            System.out.println("Error at finding Req class type.");
        }

//        backHello = getRequest();

        if(!backHello.getReqValue().equals(HLO)) {
            stopConn();
            System.out.println("Connection confirmation not received.");
            System.out.println("Stopping connection...");
        }
    }

    public boolean sayByeBye() {
        quickMessage(BYE);
        Req bye_mess = getRequest();

        return bye_mess.getReqValue().equals(BYE);
    }

    public boolean accRequest(User user, String type) {
        Req answer = null;

        quickMessage(type);
        answer = getRequest();

        if(!answer.getReqValue().equals(SOR))
            return false;

        sendClass(user);
        answer = getRequest();

        return answer.getReqValue().equals(OK);
    }


    private Req getRequest() {
        Req newRequestMessage = new Req();

        try {
            newRequestMessage = (Req)inFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("ConnToServer::getRequest\t" +
                    Thread.currentThread().getName());
            System.out.println("Failed reading message from server.");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

            System.out.println("ConnToServer::getRequest\t" +
                    Thread.currentThread().getName());
            System.out.println("Error at finding the Req class type.");
        }

        return newRequestMessage;
    }

    private void quickMessage (String mess) {
        try {

            Req quick = new Req(mess);
            outToServer.writeObject(quick);

        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("ConnToServer::quickMessage\t"
                    + Thread.currentThread().getName());
            System.out.println("Failed sending " +
                    mess.toUpperCase() + " message to client.");
        }
    }
    private void sendClass (User user) {
        try {

            outToServer.writeObject(user);

        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("ConnToServer::sendClass\t"
                    + Thread.currentThread().getName());
            System.out.println("Failed sending  User class " +
                    "to server.");
        }
    }

    void stopConn() {
        try {
            quickMessage(BYE);
            Req bye_mess = getRequest();

            sessionActive = false;
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("ConnToServer::stop\t" +
                    Thread.currentThread().getName());
            System.out.println("Failed at closing the socket.");
        }
    }
}

