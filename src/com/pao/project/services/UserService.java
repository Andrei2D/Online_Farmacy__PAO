package com.pao.project.services;

import com.pao.project.manager.Manageable;
import com.pao.project.manager.Manager;
import com.pao.project.manager.ProductCodes;
import com.pao.project.products.types.Equipment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class UserService implements ProductCodes {


    private static final int NR_OF_CLASSES = 9;
    private int type = 0;
    private Scanner cin;
    private ArrayList<Manager> managers;



    public UserService() {
        cin = new Scanner(System.in);
        managers = new ArrayList<>();
        for (int ind = 0; ind < NR_OF_CLASSES; ind++) {
            managers.add(new Manager());
        }
    }

    public void readTest() throws
            InputMismatchException, IOException{


        int option = -1;
        doStuff(option);

        while(option != 0) {
            outputs();
                // Why this approach?
            // Because might be fields that read a whole line but
            //  the file pointer will be just behind the end of the line
            // That resulting in reading an empty string
            //  instead of a line that might be find after
            option = cin.nextInt();
            cin.nextLine();
            doStuff(option);
        }
    }

    private int whatType() {
        switch (type){
            case 0:
                return USER;
            case 1:
                return PILLS;
            case 2:
                return OINTMENT;
            case 3:
                return NATURIST;
            case 4:
                return SUPPLEMENTS;
            case 5:
                return STERILE;
            case 6:
                return TEA;
            case 7:
                return SYRUP;
            case 8:
                return EQUIPMENT;
            default:
                return 0;
        }
    }

    private Manager whatManager() {

        return managers.get(type);
    }

    private String whatOption() {
        switch (type) {
            case 0:
                return "Users";
            case 1:
                return "Pills";
            case 2:
                return "Ointments";
            case 3:
                return "Naturists";
            case 4:
                return "Supplements";
            case 5:
                return "Sterile";
            case 6:
                return "Teas";
            case 7:
                return "Syrups";
            case 8:
                return "Equipments";
            default:
                return "Error";
        }
    }

    void chooseType() {
        System.out.println("\tChoose type: ");
        for (type = 0; type < NR_OF_CLASSES; type++) {
            System.out.println((type + 1) + ". " + whatOption());
        }

        type = cin.nextInt() - 1;
    }

    /** THE MENU OPTIONS*/
    private void outputs() {
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
    private void createObject(Manager manager) throws IOException{
        System.out.println("###");

        System.out.println("Input necesary data:\n");
        manager.inputData(cin, whatType());
        String userCreated = manager.get(-1).getName();
        System.out.println("Element " + userCreated + " was created !" );

        System.out.println("###");
    }

    /** 2. Output names in manager */
    private void showmanager(Manager manager) {
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
    private void showmanagerByReference(Manager manager) {
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
    private void importmanager(Manager manager) {
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
    private void exportmanager(Manager manager) {
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



    private void doStuff(int opt) throws IOException {
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
