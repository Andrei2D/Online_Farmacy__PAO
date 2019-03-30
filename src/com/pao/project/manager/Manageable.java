package com.pao.project.manager;

import com.sun.javafx.geom.transform.Identity;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public interface Manageable {
    String path = "src/com/pao/project/files/";

    String[] dataToStore();

    default void exportData(FileWriter fout) throws IOException {
        String[] data = dataToStore();
        for(String string : data) {
            fout.write(string);
            fout.write(" ");
        }
        fout.write("\n");
    }

    int getClassMask();
    String[] importData (Scanner fin) throws IOException;
    void incrementalSetter (String[] data);
    void nonIncrementalSetter(String[] data);

}
