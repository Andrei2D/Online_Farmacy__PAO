package com.pao.project.side.client;

import com.pao.project.actors.Client;
import com.pao.project.actors.User;
import com.pao.project.connection.req.Req;
import com.pao.project.connection.req.ReqConstants;
import com.pao.project.gui.clientfirstpage.ClientMainPage;
import com.pao.project.gui.createacc.CreateAccount;
import com.pao.project.gui.loginframe.LogInPage;
import com.pao.project.services.ClientService;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientSide {
    static JFrame window;
    static User currentUser = null;
    static ClientService service;
    public static void main(String[] args) {
        service = ClientService.getInstance();

        window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ClientSide.stopStuff();
            }
        });

        openLogInPage();
    }

    public static void openLogInPage() {
        LogInPage page = new LogInPage();
        window.setContentPane(page.getMainPanel());
        window.setTitle("Log in page");
        window.pack();
        window.setVisible(true);

    }

    static public void openCreateAccPage() {
        CreateAccount account = new CreateAccount();
        window.setContentPane(account.getMainPanel());
        window.setTitle("Create account");
        window.pack();
        window.setVisible(true);
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void openMainPage() {
        ClientMainPage page = new ClientMainPage(currentUser.getUsername());
        window.setContentPane(page.getMainPanel());
        window.setTitle("Pagina principala");
        window.pack();
        window.setVisible(true);

    }

    static public void stopStuff() {
        ClientService.getInstance().connToServer.stopConn();
        window = null;
    }
}
