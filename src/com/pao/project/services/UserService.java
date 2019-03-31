package com.pao.project.services;

import com.pao.project.actors.User;
import com.pao.project.manager.Manageable;
import com.pao.project.manager.Manager;
import com.pao.project.manager.Mask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserService {

    Scanner cin = new Scanner(System.in);
    Manager user = new Manager();
    Manager pills = new Manager();
    Manager ointment = new Manager();

    private int type = 0;

    int whatType() {
        switch (type){
            case 0:
                return Mask.User.getMask();
            case 1:
                return Mask.Pills.getMask();

            default:
                return  Mask.Ointment.getMask();
        }
    }

    Manager whatManager() {

        switch (type) {
            case 0:
                return user;
            case 1:
                return pills;
            default:
                return ointment;
        }
    }

    String whatOption() {
        switch (type) {
            case 0:
                return "Users";
            case 1:
                return "Pills";
            default:
                return "Ointment";
        }
    }

    void chooseType() {
        System.out.println("\tChoose type: ");
        System.out.println("1. User");
        System.out.println("2. Pills");
        System.out.println("3. Ointment");

        type = cin.nextInt() - 1;
    }

    public void readTest() throws
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
    void outputs() {
        System.out.println("*** " + whatOption() + " ***");
        System.out.println("1. Create new object");
        System.out.println("2. Show objects created");
        System.out.println("3. Show infos about objects");
        System.out.println("4. Import data");
        System.out.println("5. Export data");
        System.out.println("6. Switch type");
        System.out.println("0. Exit");
        System.out.println("***");
    }

    /** 1. Create new user */
    void createObject (Manager manager) throws IOException{
        System.out.println("###");

        System.out.println("Input necesary data:\n");
        manager.inputData(cin, whatType());
        String userCreated = manager.get(-1).getName();
        System.out.println("User " + userCreated + " was created !" );

        System.out.println("###");
    }

    /** 2. Show manager */
    void showmanager(Manager manager) {
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
    void showmanagerByReference (Manager manager) {
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
    void importmanager(Manager manager) {
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
    void exportmanager(Manager manager) {
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
    void removeUser (){

    }



    void doStuff (int opt) throws IOException {
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

            case 6:
                chooseType();
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
