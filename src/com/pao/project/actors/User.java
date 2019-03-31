package com.pao.project.actors;

import java.io.IOException;
import java.util.Scanner;

import com.pao.project.manager.*;


public class User implements Manageable {
    //protected in later versions...
    static IDentity iDentity;

    static {
        iDentity = new IDentity(Mask.User.getMask());
    }


    protected Integer uniqID;
    protected String username;
    protected String password;

    public User() {
        username = password = "";
    }

    //DELETE-ABLE
    protected User(String username, String password) {


        this.username = username;
        this.password = password;

        uniqID = iDentity.incrementalIndexing(this);
        iDentity.setNameForID(this.username, this.uniqID);
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
    public String[] importData(Scanner fin) throws IOException {
        String[] data = new String[3];

        if(fin.hasNext("(\\d+)"))
                data[0] = String.valueOf(fin.nextInt());
        data[1] = fin.next();
        data[2] = fin.next();
        fin.nextLine();

        return data;
    }

    @Override
    public void incrementalSetter(String[] data) {
        this.uniqID = iDentity.incrementalIndexing(this);
        this.username = data[1];
        this.password = data[2];
        iDentity.setNameForID(this.username, this.uniqID);
    }

    @Override
    public void nonIncrementalSetter(String[] data) {
        this.uniqID = Integer.parseInt(data[0]);
        this.username = data[1];
        this.password = data[2];
        iDentity.nonIncrementalIndexing(this, this.uniqID);
        iDentity.setNameForID(this.username, this.uniqID);
    }

    @Override
    public int getClassMask() {
        return Mask.User.getMask();

    }
}