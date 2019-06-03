package com.pao.project.actors;

import java.util.Scanner;

import com.pao.project.manager.*;


public class User implements Manageable {

    public static final int serialVersionUID = 12548;
    static protected int IDReference = 1;

    protected transient Integer uniqID;
    protected transient String username;
    protected transient String password;

    public User() {
        this(-1, "","");
    }

    public User(int uniqID, String username, String password) {
        this.uniqID = uniqID;
        updateIDRef(uniqID);
        this.username = username;
        this.password = password;
    }

    protected static int genID() {
        return IDReference++;
    }

    protected static void updateIDRef(int anID) {
        IDReference = Integer.max(anID + 1, IDReference);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        String ID = "ID: " + uniqID;
        String usr = "Username: " + username;
        String pass = "Password: " + password;
        return "U | " + ID + " | " + usr + " | " + pass ;
    }

    public static String createUser() {
        Scanner lineScanner = new Scanner(System.in);

        System.out.print("Insert username: ");
        String username = lineScanner.nextLine();

        System.out.print("Insert password: ");
        String password = lineScanner.nextLine();

        new User(genID(), username, password);

        return username;
    }

    @Override
    public String[] dataToStore() {

        String[] data = new String[3];
        data[0] = String.valueOf(uniqID);
        data[1] = username;
        data[2] = password;

        return  data;
    }

    @Override
    public void setData(String[] data) {
        uniqID = Integer.valueOf(data[0]);
        username = data[1];
        password = data[2];

    }

    @Override
    public Integer getID() {
        return uniqID;
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

        System.out.println("Input username: ");
        data[0] = fin.next();
        System.out.println("Input password: ");
        data[1] = fin.next();

        return data;
    }

}