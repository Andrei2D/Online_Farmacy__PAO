package com.pao.project.manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 The goal of this class is for each class to have a identity of
 the objects that have been created of that type and to properly generate
 ID's relative to a mask that each class will have specific

*/
public class IDentity {

    public int ID_reference;
    private int ID_mask;

    //EXPORT SPECIFIC
    private  int elem_numb = 0;

    private HashMap<Integer, Object> objectHashMap = new HashMap<Integer, Object>();
    private HashMap<String, Integer> namesHashMap = new HashMap<String, Integer>();

    public  IDentity (int ID_mask) {
        this(ID_mask, 1);
    }

    public IDentity(int ID_mask, int ID_reference) {

        this.ID_mask = ID_mask;
        this.ID_reference = ID_reference;
    }

    int setIDreference(int new_ID_Reference) {
        if(new_ID_Reference > ID_reference) {
            int prev = ID_reference;
            ID_reference = new_ID_Reference;
            return prev;
        }
        return  0;
    }

    public int getIDreference () {
        return ID_reference;
    }

    public int incrementalIndexing (Object context) {
        int ID = ID_mask | ID_reference;
        ID_reference++;
        nonIncrementalIndexing(context, ID);
        return ID;
    }


    public void nonIncrementalIndexing(Object context, int ID) {
        objectHashMap.put(ID, context);
        elem_numb++;
    }

    /** I WILL NEED AN ERROR TYPE FOR THAT*/
    public boolean importElement(Object context, String name, Integer ID) {
        if(!objectHashMap.containsKey(ID) &&
                !namesHashMap.containsKey(name)) {
            objectHashMap.put(ID, context);
            namesHashMap.put(name, ID);
            setIDreference(ID + 1);
        }
        return true;
    }

    public boolean setNameForID (String name, Integer ID) {
        if(!objectHashMap.containsKey(ID)) {
            System.out.println("NOT GOOD!!!");

            return false;}

        namesHashMap.put(name, ID);
        return true;
    }

    public boolean removeElement(Integer ID) {
        if(!objectHashMap.containsKey(ID))
            return false;
        objectHashMap.remove(ID);
        return true;
    }

    public boolean removeElement(String name) {
        if(!namesHashMap.containsKey(name))
            return false;
        Integer ID = namesHashMap.get(name);

        if(!objectHashMap.containsKey(ID))
            return false;

        objectHashMap.remove(namesHashMap.get(name));
        namesHashMap.remove(name);

        elem_numb--;

        return true;
    }

    public Object getElement(Integer ID) {
        if(!objectHashMap.containsKey(ID))
            return null;
        return objectHashMap.get(ID);
    }

    public Object getElement(String username) {
        if(!namesHashMap.containsKey(username))
            return null;
        Integer ID = namesHashMap.get(username);

        return getElement(ID);
    }

}
