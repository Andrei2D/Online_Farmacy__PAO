package com.pao.project.manager;

import com.pao.project.actors.*;
import com.pao.project.products.types.Naturist;
import com.pao.project.products.types.Ointment;
import com.pao.project.products.types.Pills;
import com.pao.project.products.types.Supplement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager {
    private ArrayList<Manageable> itemsList;
    public Manager () {
        itemsList = new ArrayList<>();
    }
    public int size() {
        return itemsList.size();
    }

    public Manageable get (int index) {
        if(itemsList.isEmpty() || index >= size())
            return null;
        //To allow .get(-1) as last item and so on
        while(index < 0) index = size() + index;

        return itemsList.get(index);
    }


    /** Method to be called on startup
     */
    public void loadData (String importFile) throws
            FileNotFoundException, IOException {
        int nr_of_elements, class_mask;
        Scanner fin = new Scanner(new File(Manageable.path + importFile));

        nr_of_elements = fin.nextInt();
        class_mask = fin.nextInt();

        System.out.println("Nr of elem: " + nr_of_elements);
        System.out.println("Class mask: " + class_mask);

        itemsList = new ArrayList<>();
        System.out.println("B4 read size: " + size());

        for(int index = 0; index < nr_of_elements; index++) {
            Manageable toLoad = newElementByMask(class_mask);
            String[] data = toLoad.importData(fin);
            if(data == null)
                System.out.println("\t#NULL DATA, WTF!#");
            toLoad.nonIncrementalSetter(data);

            itemsList.add(toLoad);
            System.out.println("\tImported: " + toString());
        }
        System.out.println("B4 read size: " + size());
        fin.close();

    }
        /** It should overwrite existing file*/
    public boolean saveData (String fileName) throws IOException {
        File file = new File(Manageable.path + fileName);

        //                      \/ - this will not be needed soon
        if(file.exists() && !itemsList.isEmpty() && !file.createNewFile())
            return false;
        FileWriter fout = new FileWriter(file);
        fout.write(itemsList.size() + " " + itemsList.get(0).getClassMask() + "\n");

        System.out.println("# EXPORT #");

        for(Manageable toStore : itemsList) {
            toStore.exportData(fout);
        }
        fout.close();
        System.out.println("#end|export#");
        return true;
    }

    public Manageable inputData(Scanner cin, int classMask) throws IOException{
        Manageable item = newElementByMask(classMask);
        item.incrementalSetter(item.inputData(cin));
        itemsList.add(item);
        return item;
    }

    private Manageable newElementByMask (int mask) {

        if (Mask.User.getMask() == mask) {
            return new User();
        }
        else if (Mask.Admin.getMask() == mask) {
            return new Admin();
        }
        else if (Mask.Client.getMask() == mask) {
            return new Client();
        }
        else if (Mask.Pills.getMask() == mask) {
            return new Pills();
        }
        else if (Mask.Ointment.getMask() == mask) {
            return new Ointment();
        }
        else if (Mask.Naturist.getMask() == mask) {
            return new Naturist();
        }
        else if (Mask.Supplement.getMask() == mask) {
            return new Supplement();
        }

        return null;
    }


}
