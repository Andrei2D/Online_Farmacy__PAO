package com.pao.project.side.server;

import com.pao.project.actors.Client;
import com.pao.project.actors.User;
import com.pao.project.connection.req.Req;
import com.pao.project.connection.req.ReqConstants;
import com.pao.project.manager.ProductCodes;
import com.pao.project.services.ServerService;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ServerSide {
    int ind;
    int maxClients;
    ArrayList<Socket> clientSockets;

    static ServerService service;
    public ServerSide() {
        this(1);
    }

    public ServerSide(int howMany) {
        maxClients = howMany;
        clientSockets = new ArrayList<>();
        ind = 0;
    }

    public static void main(String[] args) {
        service = ServerService.getInstance();
        Thread connThread = new Thread(service::initConnection);
        connThread.start();

        try {
            connThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

