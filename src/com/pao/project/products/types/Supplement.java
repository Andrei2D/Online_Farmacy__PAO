package com.pao.project.products.types;

import com.pao.project.manager.ProductCodes;
import com.pao.project.products.Product;

import java.util.Scanner;

public class Supplement extends Product {
    private String[] vitains = new String[0];

    @Override
    protected int nrOfData() {
        /** Fields superclass passed (minus description),
         number of vitains,
         vitains,
         description
         */
        return super.nrOfData() + 1 + vitains.length + 1;
    }



    @Override
    public String[] dataToStore() {
        String[] oldData = super.dataToStore();
        String[] newData = new String[nrOfData()];
        int old = super.nrOfData();
        if (old >= 0) System.arraycopy(oldData, 0, newData, 0, old);

        newData[old] = String.valueOf(vitains.length);
        for (int index = 0; index <vitains.length; index++)
            newData[old + index + 1] = vitains[index];

        newData[newData.length - 1] = this.description;
        return newData;
    }

    @Override
    public int getClassMask() { return SUPPLEMENTS; }

    @Override
    public void fillTheRest(String[] data) {
        int old = super.nrOfData();
        vitains = new String[Integer.parseInt(data[old])];

        for (int index = 0; index < vitains.length; index++)
            vitains[index] = data[old + index + 1];

        super.fillTheRest(data);
    }

    @Override
    public void setData(String[] data) {
        super.setData(data);
        fillTheRest(data);
    }

    @Override
    public String[] inputData(Scanner fin) {
        String[] oldData = super.inputData(fin);
        System.out.print("Input the NR OF VITAMINS:  ");
        int vitamins = fin.nextInt();
        fin.nextLine();

        String[] newData = new String[super.nrOfData() + vitamins + 1 + 1];
        int old = super.nrOfData();
        if (old >= 0) System.arraycopy(oldData, 0, newData, 0, old);

        newData[old] = String.valueOf(vitamins);

        System.out.println("Input the " + newData[old] + " vitamims:   ");
        for (int index = 0; index < Integer.parseInt(newData[old]); index++) {
            System.out.print(index + 1 + ". ");
            newData[old + index + 1] = fin.nextLine();
        }

        System.out.println("Input the description: ");
        newData[newData.length - 1] = fin.nextLine();

        return newData;
    }
}
