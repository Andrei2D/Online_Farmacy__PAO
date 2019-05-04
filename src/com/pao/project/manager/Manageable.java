package com.pao.project.manager;


import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public interface Manageable extends ProductCodes {
    String path = "res/csv/";
    String csv_delimiter = ", |\n";
    String csv_separator = ", ";

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
                fout.write(csv_separator);
            String dataToWrite = removeCommas(data[index]);
            fout.write(dataToWrite);
        }
        fout.write("\n");
    }

    default String[] importData (String line) throws IOException {
        LinkedList<String> importedData = new LinkedList<>();

        Scanner elmScan = new Scanner(line);
        elmScan.useDelimiter(csv_delimiter);
        while(elmScan.hasNext()) {
            String redElement = elmScan.next();
            String elmToStore = revertCommas(redElement);
            importedData.add(elmToStore);
        }
        elmScan.close();

        String[] outputData = new String[importedData.size()];
        importedData.toArray(outputData);
        return outputData;
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

