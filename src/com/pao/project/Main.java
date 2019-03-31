package com.pao.project;

import com.pao.project.actors.User;
import com.pao.project.manager.Manageable;
import com.pao.project.manager.Manager;
import com.pao.project.manager.Mask;
import com.pao.project.products.types.Pills;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static Scanner cin;
    static Manager user = new Manager();
    static Manager pills = new Manager();

    static private int type = 0;

    static int whatType() {
        if(type == 0) return Mask.User.getMask();
        else return Mask.Pills.getMask();
    }

    static Manager whatManager() {
        if(type == 0) return user;
        else return pills;
    }

    static String whatOption() {
        if(type == 0) return "Users";
        else return "Pills";
    }

    public static void main(String[] args) throws IOException {
        cin = new Scanner(System.in);

        readTest();
    }

    static void chooseType() {
        System.out.println("\tChoose type: ");
        System.out.println("1. User");
        System.out.println("2. Pills");

        type = cin.nextInt() - 1;
    }

    static void readTest() throws
            InputMismatchException, IOException{


        int option = -1;
        doStuff(option);

        while(option != 0) {
            outputs();
            option = cin.nextInt();
            doStuff(option);
        }
    }

    /** THE MENU OPTIONS*/
    static void outputs() {
        System.out.println("*** " + whatOption() + " ***");
        System.out.println("1. Create object");
        System.out.println("2. Show manager");
        System.out.println("3. Show infos about manager");
        System.out.println("4. Import data");
        System.out.println("5. Export data");
        System.out.println("0. Exit");
        System.out.println("***");
    }

    /** 1. Create new user */
    static void createObject (Manager manager) throws IOException{
        System.out.println("###");

        System.out.println("Input necesary data:\n");
        manager.inputData(cin, whatType());
        String userCreated = manager.get(-1).getName();
        System.out.println("User " + userCreated + " was created !" );

        System.out.println("###");
    }

    /** 2. Show manager */
    static void showmanager(Manager manager) {
        if(manager.size() == 0) {
            System.out.println("# Manager is empty #");
            return;
        }

        System.out.println("##");

        System.out.println("\t Infos of the object created: ");

        for (int i = 0; i < manager.size(); i++) {
            Manageable currElm = manager.get(i);
                System.out.println("\t\t" + currElm.getName());
        }
        System.out.println("##");
    }

    /** 3. Show infos about object*/
    static void showmanagerByReference (Manager manager) {
        if(manager.size() == 0) {
            System.out.println("# Manager is empty #");
            return;
        }

        System.out.println("##");

        System.out.println("\t Objects created so far: ");
        for (int i = 0; i < manager.size(); i++) {
            Manageable object = manager.get(i);
            System.out.println(i+1 +")\n" + object.toString() + "\n");
        }

        System.out.println("##");
    }

    /** 4. Import manager */
    static void importmanager(Manager manager) {
        System.out.print("Input a filename: ");
        String file = cin.next();
        try {
            manager.loadData(file);
        } catch (FileNotFoundException e) {
            System.out.println("Umm.. the file does not exist");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Incomplete data");
        }
        if(manager.size() != 0)
            System.out.println("Import successful !");

    }

    /** 5. Export manager*/
    static void exportmanager(Manager manager) {
        System.out.println("Input a filename: ");
        String file = cin.next();
        boolean done = false;
        try {
            done = manager.saveData(file);
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
            case -1:
                chooseType();
                break;
            case 1 :
                createObject(whatManager());
                break;

            case 2:
                showmanager(whatManager());
                break;

            case 3 :
                showmanagerByReference(whatManager());
                break;

            case 4:
                importmanager(whatManager());
                break;

            case 5:
                exportmanager(whatManager());
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
