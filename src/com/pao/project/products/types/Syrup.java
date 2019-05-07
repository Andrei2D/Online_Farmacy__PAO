package com.pao.project.products.types;

import com.pao.project.manager.Manageable;
import com.pao.project.products.Product;

import java.util.Scanner;

public class Syrup extends Product implements Manageable {
    public static final int ADULT = 0xA;
    public static final int BABIES = 0xB;
    public static final int CHILDREN = 0xC;
    public static final int TEENAGER = 0xD;
    public static final int EVERYONE = 0xE;

    private int targetPatients;
    private double quantity;

    @Override
    protected int nrOfData() {
        /* ID, name, price, receip -> super
         * targetPatients,
         * quantity,
         * description
         */

        return super.nrOfData() + 3;
    }

    @Override
    public String[] dataToStore() {
        String[] superData = super.dataToStore();
        String[] data = new String[nrOfData()];

        int superIndex = super.nrOfData();
        System.arraycopy(superData, 0, data, 0, superIndex);

        data[superIndex]     = String.format("%02X", targetPatients);
        data[superIndex + 1] = String.valueOf(quantity);
        data[superIndex + 2] = description;

        return data;

    }

    @Override
    public int getClassMask() {
        return SYRUP;
    }

    @Override
    public void fillTheRest(String[] data) {
        int superIndex = super.nrOfData();

        targetPatients = Integer.parseInt(data[superIndex], 16);
        quantity = Double.parseDouble(data[superIndex + 1]);

        super.fillTheRest(data);
    }

    @Override
    public void setData(String[] data) {
        super.setData(data);
        fillTheRest(data);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String[] inputData(Scanner fin) {
        String[] data = super.inputData(fin);
        int superIndex = super.nrOfData();

            /// targetPatients
        int option = 0;
        while (true) {
            System.out.println("Choose target patients by age: ");
            System.out.println("\t 1. Babies (0-2)" +
                    "\n\t 2. Children (2-10)" +
                    "\n\t 3. Teenagers (11-18)" +
                    "\n\t 4. Adults (18+)" +
                    "\n\t 5. Everyone");
            option = fin.nextInt();

            if(option >= 1 && option <= 5) {
                switch (option) {
                    case 4:
                        data[superIndex] = String.format("%02X",ADULT);
                        break;
                    case 1:
                        data[superIndex] = String.format("%02X",BABIES);
                        break;
                    case 2:
                        data[superIndex] = String.format("%02X",CHILDREN);
                        break;
                    case 3:
                        data[superIndex] = String.format("%02X",TEENAGER);
                        break;
                    case 5:
                        data[superIndex] = String.format("%02X",EVERYONE);
                        break;
                }
                break;
            }else
                System.out.println("\t > Invalid option. Choose again <");
        }
            System.out.println("Input the quantity of the bottle(ml):");
            data[superIndex + 1] = String.valueOf(fin.nextDouble());

            fin.nextLine();

            System.out.println("Input description:");
            data[superIndex + 2] = fin.nextLine();

        return data;
    }
}
