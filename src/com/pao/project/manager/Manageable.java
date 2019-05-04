package com.pao.project.manager;


import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public interface Manageable {
    String path = "res/csv";
    String csv_delimiter = ", ";

    String toString();

    /*  This generates an array with all the fields that the class
     wants to have written into the file according to */
    String[] dataToStore();
    void setData(String[] data);

    String getName();
    int getClassMask();

    default String removeCommas (String toRemove){
        return toRemove.replace(",","''");
    }

    default String revertCommas (String toRevert) {
        return toRevert.replace("''", ",");
    }

    default void exportData(FileWriter fout) throws IOException {
        String[] data = dataToStore();
        for (int index = 0; index < data.length; index++) {
            if(0 != index)
                fout.write(csv_delimiter);
            String dataToWrite = removeCommas(data[index]);
            fout.write(dataToWrite);
        }
        fout.write("\n");
    }

    default String[] importData (Scanner fin) throws IOException {
        LinkedList<String> importedData = new LinkedList<>();
        String line = fin.nextLine();

        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(csv_delimiter);

        while(scanner.hasNext()) {
            String redElement = scanner.next();
            importedData.add(revertCommas(redElement));
        }
        scanner.close();

        return (String[]) importedData.toArray();
    }

    default String[] inputData(Scanner fin) {return new String[0];}

}

/* Header of the files:
 Number of elements |   Code of class type
 Data

 Everything is comma separated
 */

/*
    What's the role of every function?


    How will the the importation and exportation work?

    1. Every class will generate his own array of
 */

