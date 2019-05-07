package com.pao.project.products.types;

import com.pao.project.products.Product;

import java.util.Scanner;

public class Tea extends Product {

        //  If either has tea bags or just plain herbs
    private boolean teaBags;
        // Either a number of tea bags or an amount of grams
    private double quantity;

    @Override
    protected int nrOfData() {
        /* ID, name, price, receip -> super
         * teaBags,
         * quantity,
         * description
         */
        return super.nrOfData() + 3;
    }

    @Override
    public String[] dataToStore() {
        String[] superData = super.dataToStore();
        String[] data = new String[nrOfData()];
        int lastOldInd = super.nrOfData();
        System.arraycopy(superData, 0, data, 0, lastOldInd);

        data[lastOldInd]     = String.valueOf(teaBags);
        data[lastOldInd + 1] = String.valueOf(quantity);
        data[lastOldInd + 2] = description;

        return data;
    }

    @Override
    public int getClassMask() {
        return TEA;
    }

    @Override
    public void setData(String[] data) {
        super.setData(data);
        fillTheRest(data);
    }

    @Override
    public void fillTheRest(String[] data) {
        int superIndex = super.nrOfData();

        teaBags  = Boolean.parseBoolean(data[superIndex]);
        quantity = Double.parseDouble(data[superIndex + 1]);

        super.fillTheRest(data);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String[] inputData(Scanner fin) {
        String[] data = super.inputData(fin);
        int superIndex = super.nrOfData();

        int type = 0;
        while(true) {
            System.out.println("Choose the type of the tea: + " +
                    "\n\t1. Tea bags" + "\n\t2. Tea package");
            type = fin.nextInt();
            if(type == 1 || type == 2) break;
            else System.out.println(">Invalid option<");
        }

        double amount = 0;
        if (type == 1) {
            data[superIndex] = Boolean.toString(true);
            System.out.println("Input the number of teabags: ");
            amount = (double) fin.nextInt();
        }else{
            data[superIndex] = Boolean.toString(false);
            System.out.println("Input the amount of tea in package (grams): ");
            amount = fin.nextDouble();
        }
        data[superIndex + 1] = String.valueOf(amount);

        fin.nextLine();

        System.out.println("Input the description:");
        data[superIndex + 2] = fin.nextLine();

        return data;
    }
}
