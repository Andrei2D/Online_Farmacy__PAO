package com.pao.project.products.types;

import com.pao.project.manager.Manageable;
import com.pao.project.products.Product;

import java.util.Scanner;

public class Equipment extends Product implements Manageable {
    private boolean isElectric;

    @Override
    protected int nrOfData() {
        /* name, price -> super
         * isElectric,
         * description
         */
        return super.nrOfData() + 2;
    }

    @Override
    public String[] dataToStore() {
        String[] superData = super.dataToStore();
        String[] data = new String[nrOfData()];
        int lastOldInd = super.nrOfData();
        System.arraycopy(superData, 0, data, 0, lastOldInd);

        data[lastOldInd]     = String.valueOf(isElectric);
        data[lastOldInd + 1] = description;

        return data;
    }

    @Override
    public int getClassMask() {
        return EQUIPMENT;
    }

    @Override
    public void fillTheRest(String[] data) {

        isElectric = Boolean.parseBoolean(
                data[super.nrOfData()]
        );

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

        int type = 0;
        while(true) {
            System.out.println("Choose the type of the equipment: " +
                    "\n\t1. Electric" + "\n\t2. Not electric");
            type = fin.nextInt();
            if(type == 1 || type == 2) {
                if (type == 1)  data[superIndex] = String.valueOf(true);
                    else        data[superIndex] = String.valueOf(false);
                break;
            }
            else System.out.println(">Invalid option<");
        }

        fin.nextLine();

        System.out.println("Input the description:");
        data[superIndex + 1] = fin.nextLine();

        return data;
    }
}
