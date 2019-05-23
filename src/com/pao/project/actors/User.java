package com.pao.project.actors;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

import com.pao.project.manager.*;


public class User implements Manageable {

    public static final int serialVersionUID = 12548;
    static protected int IDReference = 1;

    protected transient Integer uniqID;
    protected transient String username;
    protected transient String password;
    protected transient String email;
    protected transient String telephone;

    public User() {
        this("","","","");
    }

    //DELETE-ABLE
    public User(String username, String password, String email, String telephone) {
        uniqID = getID();
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
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
        String mail = "e-mail: " + email;
        String phone = "Telephone: " + telephone;
        return ID + " | " + usr + " | " + pass + " | " + mail + " | " + phone;
    }

    public static String createUser() {
        Scanner lineScanner = new Scanner(System.in);

        System.out.print("Insert username: ");
        String username = lineScanner.nextLine();

        System.out.print("Insert password: ");
        String password = lineScanner.nextLine();

        new User(username, password,"","");

        return username;
    }

    @Override
    public String[] dataToStore() {
        String[] data = new String[5];
        data[0] = String.valueOf(uniqID);
        data[1] = username;
        data[2] = password;
        data[3] = email;
        data[4] = telephone;

        return  data;
    }

    @Override
    public void setData(String[] data) {
        int index = 0;
        if(data.length == 4) {
            uniqID = getID();
        } else {
            uniqID = Integer.valueOf(data[0]);
            updateIDRef(uniqID);
            index = 1;
        }

        username = data[index];
        password = data[index + 1];
        email = data[index + 2];
        telephone = data[index  + 3];

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