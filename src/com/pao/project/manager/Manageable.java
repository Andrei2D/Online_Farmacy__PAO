package com.pao.project.manager;

import com.sun.javafx.geom.transform.Identity;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public interface Manageable {
    String path = "src/com/pao/project/files/";

    String toString();

    String[] dataToStore();

    default void exportData(FileWriter fout) throws IOException {
        String[] data = dataToStore();
        for(String string : data) {
            fout.write(string);
            fout.write(" ");
        }
        fout.write("\n");
    }

    String getName();
    int getClassMask();

    default String[] importData (Scanner fin) throws IOException { return new String[0];}
    void incrementalSetter (String[] data);
    void nonIncrementalSetter(String[] data);

    String[] inputData(Scanner fin);

}

