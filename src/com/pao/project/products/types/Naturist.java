package com.pao.project.products.types;

import com.pao.project.manager.IDentity;
import com.pao.project.manager.Mask;
import com.pao.project.products.Product;

import java.io.IOException;
import java.util.Scanner;

public class Naturist extends Product {
    static {
        iDentity = new IDentity(Mask.Naturist.getMask());
    }
    private String[] natural_ingredients = new String[0];

    @Override
    protected int nrOfData() {
        /** Fields superclass passed (minus description),
         number of natural ingredients,
         natural ingredients,
         description
         */
        return super.nrOfData() + 1 + natural_ingredients.length + 1;
    }



    @Override
    public String[] dataToStore() {
        String[] oldData = super.dataToStore();
        String[] newData = new String[nrOfData()];
        int old = super.nrOfData();
        if (old >= 0) System.arraycopy(oldData, 0, newData, 0, old);

        newData[old] = String.valueOf(natural_ingredients.length);
        for (int index = 0; index <natural_ingredients.length; index++)
            newData[old + index + 1] = natural_ingredients[index];

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
        natural_ingredients = new String[Integer.parseInt(data[old])];

        for (int index = 0; index < natural_ingredients.length; index++)
            natural_ingredients[index] = data[old + index + 1];

        super.fillTheRest(data);
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
}
