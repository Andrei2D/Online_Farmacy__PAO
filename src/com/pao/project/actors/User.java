package com.pao.project.actors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

import com.pao.project.manager.IDsManager;

public class User {
    //must be default
    public static int class_mask ;
    public static IDsManager manager;

    static {
        class_mask = 0b1110000000000000;
        manager = new IDsManager(class_mask);
    }


    protected Integer uniqID;
    protected String username;
    protected String password;

    public User(String username, String password) {


        this.username = username;
        this.password = password;

        uniqID = manager.generateID(this);
        manager.setNameForID(this.username, uniqID);

    }

    @Override
    public String toString() {
        String ID = "ID: " + uniqID;
        String usr = "Username: " + username;
        String pass = "Password: " + password;

        return ID + " | " + usr + " | " + pass;
    }

    public static String createUser() {
        Scanner lineScanner = new Scanner(System.in);

        System.out.print("Insert username: ");
        String username = lineScanner.nextLine();

        System.out.print("Insert password: ");
        String password = lineScanner.nextLine();

        new User(username,password);


        return username;
    }


}
