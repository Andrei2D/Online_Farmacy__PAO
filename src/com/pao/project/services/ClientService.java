package com.pao.project.services;

import com.pao.project.actors.Client;
import com.pao.project.gui.loginframe.LogInPage;
import com.pao.project.manager.FreshManager;
import com.pao.project.side.client.ConnToServer;


import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ClientService extends MainService {
    public ConnToServer connToServer = null;

    static ClientService instance = null;

    public static ClientService getInstance() {
        if(null == instance)
            instance = new ClientService();
        return instance;
    }

    private ClientService() {
        manager = new HashMap<>();
        manager.put(CLIENT,
                new FreshManager<Client>());
        try {

            initConnection();

        } catch (IOException e) {
            e.printStackTrace();

            System.out.println("Connection failed initialisation, client side.");
        }
    }

    void initConnection() throws IOException {
        int port_number = 7800;

        Socket sockToServer = new Socket("localhost", port_number);
        connToServer = new ConnToServer(sockToServer);

    }
}
