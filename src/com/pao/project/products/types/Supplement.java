package com.pao.project.products.types;

import com.pao.project.manager.Mask;
import com.pao.project.manager.ProductCodes;
import com.pao.project.products.Product;

import java.io.IOException;
import java.util.Scanner;

public class Supplement extends Product {

    public static final int MASK = ProductCodes.SUPPLEMENTS;

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
    public int getClassMask() { return Mask.Naturist.getMask(); }

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
        int to_read = Integer.parseInt(data[old]);

        for (int index = 0; index < to_read; index++) {
            data[old + index + 1] = fin.next();
        }

        data[data.length - 1] = fin.nextLine();

        return data;
    }

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
}
