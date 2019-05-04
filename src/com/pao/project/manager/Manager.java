package com.pao.project.manager;

import com.pao.project.actors.*;
import com.pao.project.products.types.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static com.pao.project.manager.Manageable.csv_delimiter;
import static com.pao.project.manager.Manageable.csv_separator;

public class Manager implements ProductCodes{
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


    /** Method that will be called on startup but for now it has an
     * option in the menu*/
    public void loadData (String importFile) throws
            FileNotFoundException, IOException {
            //  Read how many items are int the file and
            // what's the class who's data are being written
        int nr_of_elements, class_mask;
        Scanner fin = new Scanner(new File(Manageable.path + importFile));
        fin.useDelimiter(csv_delimiter);

        nr_of_elements = fin.nextInt();
        class_mask = fin.nextInt();
        fin.nextLine();

        System.out.println("Nr of elem: " + nr_of_elements);
        System.out.println("Class mask: " + String.format("0x%02X", class_mask));

            // Elements are being read with each line
        fin.useDelimiter("\n");
        itemsList = new ArrayList<>();

        for(int index = 0; index < nr_of_elements; index++) {
            String line = fin.nextLine();
                // Create a new element
            Manageable toLoad = newElementByMask(class_mask);
                // Read all it's data from the file
            String[] data = toLoad.importData(line);
                // Be sure data was red
            if (null == data) continue;
                // Fill the fields with the data red
            toLoad.setData(data);
                // Add the item into the manager
            itemsList.add(toLoad);
            System.out.println("\tImported: " + toLoad.getName());
        }
        fin.close();

    }
  
        // Function which exports the contains of the Manager instance
    public boolean saveData (String fileName) throws IOException {
        File file = new File(Manageable.path + fileName);

        //                      \/ - this will not be needed soon
        if(file.exists() && !itemsList.isEmpty() && !file.createNewFile())
            return false;
        FileWriter fout = new FileWriter(file);
        fout.write(itemsList.size() + csv_separator);
        fout.write(itemsList.get(0).getClassMask() + "\n");

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
        String[] itemData = item.inputData(cin);
        item.setData(itemData);
        itemsList.add(item);
        return item;
    }

    private Manageable newElementByMask (int mask) {

        switch (mask) {
            case USER:
                return new User();
            case ADMIN:
                return new Admin();
            case CLIENT:
                return new Client();
            case PILLS:
                return new Pills();
            case OINTMENT:
                return new Ointment();
            case NATURIST:
                return new Naturist();
            case SUPPLEMENTS:
                return new Supplement();
            default:
                return null;
        }

    }


}
