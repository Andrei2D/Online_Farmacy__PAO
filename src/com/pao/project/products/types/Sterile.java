package com.pao.project.products.types;

import com.pao.project.manager.Manageable;
import com.pao.project.products.Product;

import java.util.Scanner;

public class Sterile  extends Product implements Manageable {

    private int nrOfPieces;
    private double width, height;

    @Override
    protected int nrOfData() {
        /*  Fields superclass passed (minus description),
         *  width, height,
         *  nrOfPieces,
         *  description
         */
        return super.nrOfData() + 4;
    }

    @Override
    public String[] dataToStore() {
        String[] superData = super.dataToStore();
        String[] data = new String[nrOfData()];
        int lastOldInd = super.nrOfData();
        System.arraycopy(superData, 0, data, 0, lastOldInd);

        data[lastOldInd] = String.valueOf(nrOfPieces);
        data[lastOldInd + 1] = String.valueOf(width);
        data[lastOldInd + 2] = String.valueOf(height);
        data[lastOldInd + 3] = description;
        return data;
    }

    @Override
    public int getClassMask() {
        return STERILE;
    }

    @Override
    public void setData(String[] data) {
        super.setData(data);
        fillTheRest(data);
    }

    @Override
    public void fillTheRest(String[] data) {
        int superIndex = super.nrOfData();

        nrOfPieces  = Integer.parseInt(data[superIndex]);
        width       = Double.parseDouble(data[superIndex + 1]);
        height      = Double.parseDouble(data[superIndex + 2]);

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

        System.out.println("Input the number of\n\tpieces in a pack:");
        data[superIndex] = fin.next();
        System.out.println("Input the WIDTH of a piece (cm):");
        data[superIndex + 1] = fin.next();
        System.out.println("Input the HEIGHT of a piece (cm):");
        data[superIndex + 2] = fin.next();

        fin.nextLine();

        System.out.println("Input the description:");
        data[superIndex + 3] = fin.nextLine();

        return data;
    }

}
