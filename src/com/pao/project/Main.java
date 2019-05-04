package com.pao.project;


import com.pao.project.manager.Manageable;
import com.pao.project.products.types.Pills;
import com.pao.project.services.UserService;
import jdk.nashorn.api.tree.RegExpLiteralTree;

import java.awt.font.FontRenderContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    static UserService uServ = new UserService();
    public static void main(String[] args)  {
        try {
            uServ.readTest();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static void readTest() {
        try {
            Scanner scan = new Scanner(new File(Manageable.path + "users.csv"));

            scan.nextLine();
            ArrayList<LinkedList<String>> matrix = new ArrayList<>();
            int index = 0;
            while(scan.hasNextLine()) {

                String line = scan.nextLine();
                System.out.println(line);

                Scanner elmScan = new Scanner(line);
                elmScan.useDelimiter(", |\n");
                matrix.add(new LinkedList<>());

                while(elmScan.hasNext()) {
                    String red = elmScan.next();
                    System.out.println("\t" + red);
                    matrix.get(index).add(red);
                }
                String[] arrayVariant = new String[matrix.get(index).size()];
                matrix.get(index).toArray(arrayVariant);
                afisStringArr(arrayVariant);
                System.out.println();
                index ++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FILE NOT FKING FOUND, duh >.>");
        }
    }

    static void afisStringArr(String[] data) {
        System.out.println();
        if(data == null) {
            System.out.println(">NULL STRING ARRAY<");
            return;
        }
        if (data.length == 0) {
            System.out.println(">EMPTY STRING ARRAY<");
            return;
        }
        for (String elm :
                data) {
            System.out.println(elm + "\t");
        }

    }

}
