package com.pao.project;

import com.pao.project.actors.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<String> users = new ArrayList<>();
    public static void main(String[] args) {

        int option = -1;
        Scanner cin = new Scanner(System.in);

        while(option != 0) {
            outputs();
            option = cin.nextInt();
            doStuff(option);
        }

    }

    static void outputs() {
        System.out.println("***");
        System.out.println("1. Create account");
        System.out.println("2. Show users");
        System.out.println("3. Show infos about users");
        System.out.println("0. Exit");
        System.out.println("***");
    }

    static void doStuff (int opt) {
        switch (opt) {
            case 1 :
                users.add(User.createUser());
                System.out.println("# User" + users.get(users.size() - 1) + "was created ! #");
                break;

            case 2:
                if(users.size() == 0) {
                    System.out.println("# No users created yet #");
                    break;
                }

                System.out.println("##");

                System.out.println("\t Users created so far: ");
                for (String usr : users) {
                    System.out.println("\t\t" + usr);
                }

                System.out.println("##");
                break;

            case 3 :
                if(users.size() == 0) {
                    System.out.println("# No users created yet #");
                    break;
                }

                System.out.println("##");

                System.out.println("\t Infos of users created: ");

                for (String usr : users) {
                    System.out.println("\t\t" + User.manager.getElement(usr).toString());
                }
                System.out.println("##");
                break;

            case 0:
                System.out.println("Session ended...");
                break;
        }
        System.out.println();
    }
}
