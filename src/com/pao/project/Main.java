package com.pao.project;

import com.pao.project.actors.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner cin;
    static ArrayList<String> users = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        readTest();
    }

    static void readTest() {
        int option = -1;
        cin = new Scanner(System.in);

        while(option != 0) {
            outputs();
            option = cin.nextInt();
            doStuff(option);
        }
    }

    /** THE MENU OPTIONS*/
    static void outputs() {
        System.out.println("***");
        System.out.println("1. Create account");
        System.out.println("2. Show users");
        System.out.println("3. Show infos about users");
        System.out.println("4. Show users with the ID_ref");
        System.out.println("5. Remove user");
        System.out.println("0. Exit");
        System.out.println("***");
    }

    /** 2. Show users*/
    static void showUsers () {
        if(users.size() == 0) {
            System.out.println("# No users created yet #");
            return;
        }

        System.out.println("##");

        System.out.println("\t Users created so far: ");
        for (String usr : users) {
            System.out.println("\t\t" + usr);
        }

        System.out.println("##");
    }

    /** 2. Show users from ID_Manager*/
    static void showUsersByReference() {
        if(users.size() == 0) {
            System.out.println("# No users created yet #");
            return;
        }

        System.out.println("##");

        System.out.println("\t Infos of users created: ");

        for (String usr : users) {
            User currUser = (User) User.manager.getElement(usr);
            if(null == currUser) {
                System.out.println("\t\tWTF ! NULL SHIT FOR " + usr);
            }
            else
                System.out.println("\t\t" + currUser.toString());
        }
        System.out.println("##");
    }

    static void showUsersByID() {
        System.out.println("##");

        int ref = User.manager.ID_reference;
        System.out.println("Reference: " + ref);
        for (int integ = 1; integ < ref; integ++) {
            User user = (User) User.manager.getElement(User.class_mask | integ);
            if(user == null) {
                int unNr = User.class_mask | integ;
                System.out.println("\tThis user (ID= " + unNr + ") does not exist..");
            }
            else {
                System.out.println("\t" + user.toString());
            }
        }

        System.out.println("##");

    }
        /**5. Remove user */
    static void removeUser() {
        System.out.println("##");
        System.out.println("\tInput username to remove: ");
        String username;
        username = cin.next();
        User user = (User) User.manager.getElement(username);
        if(user == null) {
            System.out.println("\tUser \'" + username + "\' does not exist");
        }
        else {
            if(User.manager.removeElement(username)) {

                for (String elem : users) {
                    if(elem.equals(username)) {
                        users.remove(elem);
                        break;
                    }

                }
                System.out.println("\tUser \'" + username + "\' removed");
            }
            else
                System.out.println("\tUser \'" + username + "\' FAILED to be removed");
        }


        System.out.println("##");
    }

    static void doStuff (int opt) {
        switch (opt) {
            case 1 :
                users.add(User.createUser());
                System.out.println("# User " + users.get(users.size() - 1) + " was created ! #");
                break;

            case 2:
                showUsers();
                break;

            case 3 :
                showUsersByReference();
                break;

            case 4:
                showUsersByID();
                break;

            case 5:
                removeUser();
                break;

            case 0:
                System.out.println("Session ended...");
                break;
        }
        System.out.println();
    }
}
