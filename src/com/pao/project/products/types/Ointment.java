package com.pao.project.products.types;

import com.pao.project.manager.Mask;
import com.pao.project.manager.ProductCodes;
import com.pao.project.products.Product;

import java.io.IOException;
import java.util.Scanner;

public class Ointment extends Product {
    public static final int MASK = ProductCodes.OINTMENT;
    private String[] application_areas = new String[0];

    @Override
    protected int nrOfData() {
        /** Fields superclass passed (minus description),
          number of application areas,
          application areas,
          description
         */
        return super.nrOfData() + 1 + application_areas.length + 1;
    }

    @Override
    public String[] dataToStore() {
        String[] oldData = super.dataToStore();
        String[] newData = new String[nrOfData()];
        int old = super.nrOfData();
        if (old >= 0) System.arraycopy(oldData, 0, newData, 0, old);

        newData[old] = String.valueOf(application_areas.length);
        for (int index = 0; index <application_areas.length; index++)
            newData[old + index + 1] = application_areas[index];

        newData[newData.length - 1] = this.description;
        return newData;
    }

    @Override
    public int getClassMask() {
        return OINTMENT;
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
        String[] data = super.importData(fin);
        int old = super.nrOfData();
        int areas = fin.nextInt();
        String[] newData = new String[old + areas + 1 + 1];
        if (old >= 0) System.arraycopy(data, 0, newData, 0, old);


        newData[old] = String.valueOf(areas);

        for (int index = 0; index < areas; index++) {
            newData[old + index + 1] = fin.next();
        }

        newData[newData.length - 1] = fin.nextLine();

        return newData;
    }

    @Override
    public void fillTheRest(String[] data) {
        int old = super.nrOfData();
        application_areas = new String[Integer.parseInt(data[old])];

        for (int index = 0; index < application_areas.length; index++)
            application_areas[index] = data[old + index + 1];

        super.fillTheRest(data);
    }

//    @Override
//    public void incrementalSetter(String[] data) {
//        super.incrementalSetter(data);
//        fillTheRest(data);
//
//    }

    @Override
    public void setData(String[] data) {
        super.setData(data);
        fillTheRest(data);
    }

    @Override
    public String[] inputData(Scanner fin) {
        String[] oldData = super.inputData(fin);
        System.out.print("Input the NR OF \n\tAREAS OF APPLICATION:  ");
        int areas = fin.nextInt();


        String[] newData = new String[super.nrOfData() + areas + 1 + 1];
        int old = super.nrOfData();
        if (old >= 0) System.arraycopy(oldData, 0, newData, 0, old);

        newData[old] = String.valueOf(areas);

        System.out.println("Input the " + newData[old] + " areas  of application:   ");
        for (int index = 0; index < Integer.parseInt(newData[old]); index++) {
            System.out.print(index + 1 + ". ");
            newData[old + index + 1] = fin.next();
        }

        fin.nextLine();

        System.out.println("Input the description: ");
        newData[newData.length - 1] = fin.nextLine();

        return newData;
    }

    @Override
    public String toString() {
        StringBuilder bigString = new StringBuilder("Type: Ointment\n");
        bigString.append(super.toString());

        if(application_areas.length > 0) {
            bigString.append("Areas of application:\n");
            for (int index = 0; index < application_areas.length; index++) {
                bigString.append("\t").append(index + 1).append(". ").append(application_areas[index]).append("\n");
            }
        }

        bigString.append("Description:\n\t").append(this.description).append("\n");

        return bigString.toString();
    }


}
