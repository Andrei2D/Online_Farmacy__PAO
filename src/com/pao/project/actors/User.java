package com.pao.project.actors;

import java.io.IOException;
import java.util.Scanner;

import com.pao.project.manager.*;


public class User implements Manageable {

    static protected int IDReference = 1;

    protected Integer uniqID;
    protected String username;
    protected String password;

    public User() {
        username = password = "";
    }

    //DELETE-ABLE
    protected User(String username, String password) {
        uniqID = getID();
        this.username = username;
        this.password = password;
    }

    protected static int getID() {
        return IDReference++;
    }

    protected static void updateIDRef(int anID) {
        IDReference = Integer.max(anID + 1, IDReference);
    }

    public String getUsername() {
        return username;
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

        new User(username, password);

        return username;
    }

    @Override
    public String[] dataToStore() {
        String[] data = new String[3];
        data[0] = uniqID.toString();
        data[1] = username;
        data[2] = password;
        return  data;
    }

    @Override
    public void setData(String[] data) {
        uniqID   = Integer.parseInt(data[0]);
        username = data[1];
        password = data[2];

        updateIDRef(uniqID);
    }

    @Override
    public String getName() {
        return getUsername();
    }



    @Override
    public int getClassMask() {
        return USER;

    }

    @Override
    public String[] inputData(Scanner fin) {
        String[] data = new String[3];

        data[0] = String.valueOf(getID());
        System.out.println("Input username: ");
        data[1] = fin.next();
        System.out.println("Input password: ");
        data[2] = fin.next();

        return data;
    }
}