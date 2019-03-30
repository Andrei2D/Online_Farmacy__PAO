package com.pao.project;

import com.pao.project.actors.User;
import com.pao.project.manager.Manageable;
import com.pao.project.manager.Manager;
import com.pao.project.manager.Mask;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static Scanner cin;
    static Manager users = new Manager();

    public static void main(String[] args) throws IOException {
        cin = new Scanner(System.in);

        readTest();
    }


    static void readTest() throws
            InputMismatchException, IOException{
        int option = -1;

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
        System.out.println("6. Export data");
        System.out.println("7. Import data");
        System.out.println("0. Exit");
        System.out.println("***");
    }

    /** 1. Create new user */
    static void createUser () throws IOException{
        System.out.println("###");

        System.out.println("Input the USERNAME and the PASSWORD:\n");
        users.inputData(cin, Mask.User.getMask());
        String userCreated = ((User)users.get(-1)).getUsername();
        System.out.println("User " + userCreated + " was created !" );

        System.out.println("###");
    }

    /** 2. Show users */
    static void showUsers() {
        if(users.size() == 0) {
            System.out.println("# No users created yet #");
            return;
        }

        System.out.println("##");

        System.out.println("\t Infos of users created: ");

        for (int i = 0; i < users.size(); i++) {
            User currUser = (User) users.get(i);
                System.out.println("\t\t" + currUser.getUsername());
        }
        System.out.println("##");
    }

    /** 3. Show infos about users*/
    static void showUsersByReference () {
        if(users.size() == 0) {
            System.out.println("# No users created yet #");
            return;
        }

        System.out.println("##");

        System.out.println("\t Users created so far: ");
        for (int i = 0; i < users.size(); i++) {
            User usr = (User) users.get(i);
            System.out.println("\t\t" + usr);
        }

        System.out.println("##");
    }

    /** 4. Import users */
    static void importUsers() {
        System.out.print("Input a filename: ");
        String file = cin.next();
        try {
            users.loadData(file);
        } catch (FileNotFoundException e) {
            System.out.println("Umm.. the file does not exist");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Incomplete data");
        }
        if(users.size() != 0)
            System.out.println("Import successful !");

    }

    /** 5. Export users*/
    static void exportUsers() {
        System.out.println("Input a filename: ");
        String file = cin.next();
        boolean done = false;
        try {
            done = users.saveData(file);
        }
        catch (IOException e) {
            System.out.println("EXPORT FAILED: File already exists");
        }
        finally {
            if(done) System.out.println("Exportation done");
            else System.out.println("Exportation failed");
        }

    }

    /** To do */
    static void removeUser (){

    }

    static void doStuff (int opt) throws IOException {
        switch (opt) {
            case 1 :

                createUser();
                break;

            case 2:
                showUsers();
                break;

            case 3 :
                showUsersByReference();
                break;

            case 4:
                importUsers();
                break;

            case 5:
                exportUsers();
                break;

            case 0:
                System.out.println("Session ended...");
                break;

            default:
                System.out.println("> INVALID OPTION ! <");
                break;
        }
        System.out.println();
    }
}
