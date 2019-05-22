package com.pao.project.manager;

import com.pao.project.actors.*;
import com.pao.project.products.types.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;

import static com.pao.project.manager.Manageable.csv_delimiter;
import static com.pao.project.manager.Manageable.csv_separator;

public class Manager implements ProductCodes{
    public static HashMap<String, Manageable> elmByName = new HashMap<>();
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

    public String remove(int index) {
        if(index >= itemsList.size())
            return "";
        String nameOfRemoved = itemsList.get(index).getName();
        itemsList.remove(index);
        return nameOfRemoved;
    }

    /**
     *  Function that reads a header containing a number a elements and
     * a class mask and then the number of elements said in the header
     * of the class having the class mask
     *
     * @param fin Scanner variable who's pointer is right
     *            before the header
     */
    public int importData(Scanner fin) throws
            IOException {
        int nr_of_elements, class_mask, succesfully_imported = 0;
        Scanner headerScanner = new Scanner(fin.nextLine());
        headerScanner.useDelimiter(csv_delimiter);

        nr_of_elements = headerScanner.nextInt();
        class_mask = headerScanner.nextInt(16);
        
        headerScanner.close();

        System.out.println("Nr of elem: " + nr_of_elements);
        System.out.println("Class mask: " + String.format("0x%02X", class_mask));

        if(nr_of_elements == 0) return  0;

        // Elements are being read with each line

        for(int index = 0; index < nr_of_elements; index++) {
            String line = fin.nextLine();
            // Create a new element
            Manageable toLoad = newElementByMask(class_mask);
            // Read all it's data from the file
            if(toLoad == null) System.out.println("WTF");
            String[] data = toLoad.importData(line);
            // Be sure data was red
            if (null == data) continue;
            if(elmByName.containsKey(data[0])) {
                System.out.println("Object >" + data[0] + "< attempted to be red again.");
                continue;
            }

            // Fill the fields with the data red
            toLoad.setData(data);
            // Add the item into the manager
            itemsList.add(toLoad);
            elmByName.put(toLoad.getName(), toLoad);
            succesfully_imported ++;
//            System.out.println("\tImported: " + toLoad.getName());
        }

        return succesfully_imported;
    }

    /** Method that will be called on startup but for now it has an
     * option in the menu*/
    public int loadData (String importFile) throws
            FileNotFoundException, IOException {
            //  Read how many items are int the file and
            // what's the class who's data are being written
        Scanner fin = new Scanner(new File(Manageable.path + importFile));

        int succesful_imports = importData(fin);

        fin.close();

        return succesful_imports;

    }

        // Function which exports the contains of the Manager instance
    public boolean saveData (String fileName) throws IOException {
        File file = new File(Manageable.path + fileName);

        //                      \/ - this will not be needed soon
        if(file.exists() && !itemsList.isEmpty() && !file.createNewFile())
            return false;
        FileWriter fout = new FileWriter(file);
        fout.write(itemsList.size() + csv_separator);
        fout.write(String.format("%02X", itemsList.get(0).getClassMask()) + "\n");

        System.out.println("# EXPORT #");

        for(Manageable toStore : itemsList) {
            toStore.exportData(fout);
        }
        fout.close();
        System.out.println("#end|export#");
        return true;
    }

    public void exportData (FileWriter writer) {
        if(itemsList.size() == 0) return;
        try {
                // Header
            writer.write(itemsList.size() + csv_separator + itemsList.get(0).getClassMask() + "\n");
                //Elements
            for (Manageable element: itemsList
                 ) {
                element.exportData(writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Manageable inputData(Scanner cin, int classMask) throws IOException{
        Manageable item = newElementByMask(classMask);
        String[] itemData = item.inputData(cin);
        item.setData(itemData);
        itemsList.add(item);
        return item;
    }

    public void uniteManager (Manager manager) {
        if(manager.size() == 0) return;
        itemsList.addAll(manager.itemsList);
    }

    static private Manageable newElementByMask (int mask) {

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
            case STERILE:
                return new Sterile();
            case TEA:
                return new Tea();
            case SYRUP:
                return new Syrup();
            case EQUIPMENT:
                return new Equipment();
            default:
                return null;
        }

    }

    public int clear() {
        int to_return = itemsList.size();
        itemsList.clear();
        return  to_return;
    }


}
