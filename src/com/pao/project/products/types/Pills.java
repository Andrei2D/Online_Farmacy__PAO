package com.pao.project.products.types;

import com.pao.project.manager.IDentity;
import com.pao.project.manager.Mask;
import com.pao.project.products.Product;

import java.io.IOException;
import java.util.Scanner;

public class Pills extends Product {
    static {
        iDentity = new IDentity(Mask.Pills.getMask());
    }
    private int number_of_pills;


    @Override
    protected int nrOfData() {
        /** Fields superclass passed (minus description),
         * number of pills,
         * description
         */
        return super.nrOfData() + 1 + 1;
    }

    /** MANAGEABLE METHODS*/

    @Override
    public String[] dataToStore() {
        String[] oldData = super.dataToStore();
        int old = super.nrOfData();
        String[] newData = new String[nrOfData()];

        if (old >= 0) System.arraycopy(oldData, 0, newData, 0, old);

        newData[old] = String.valueOf(number_of_pills);
        newData[old + 1] = description;

        return newData;
    }

    @Override
    public int getClassMask() {
        return Mask.Pills.getMask();
    }

    private String[] importSuperData(Scanner fin) throws IOException {

        String[] oldData = super.importData(fin);
        String[] newData = new String[nrOfData()];
        int old = super.nrOfData();
        if (old >= 0) System.arraycopy(oldData, 0, newData, 0, old);

        return newData;
    }

    @Override
    public String[] importData(Scanner fin) throws IOException {
        String[] data = importSuperData(fin);
        int old = super.nrOfData();
        data[old] = fin.next();
        data[old + 1] = fin.nextLine();

        return data;
    }

    public void fillTheRest (String[] data) {

        int len = super.nrOfData();
        this.number_of_pills = Integer.parseInt(data[len]);
        super.fillTheRest(data);    //setting this.description
    }

    @Override
    public void incrementalSetter(String[] data) {
        super.incrementalSetter(data);
        fillTheRest(data);
    }

    @Override
    public void nonIncrementalSetter(String[] data) {
        super.nonIncrementalSetter(data);
        fillTheRest(data);
    }

    @Override
    public String toString() {
        String bigString = "Type: Pills\n";
        bigString += super.toString();
        bigString += "Description:\n\t" + this.description + "\n";

        return bigString;
    }
}