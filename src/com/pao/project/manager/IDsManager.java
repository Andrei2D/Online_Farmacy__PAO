package com.pao.project.manager;


import java.util.HashMap;
/**
 The goal of this class is for each class to have a manager of
 the objects that have been created of that type and to properly generate
 ID's relative to a mask that each class will have specific

*/
public class IDsManager {

    public int ID_reference = 1;
    private int ID_mask;
//
    private HashMap<Integer, Object> objectHashMap = new HashMap<Integer, Object>();
    private HashMap<String, Integer> namesHashMap = new HashMap<String, Integer>();

    public IDsManager(int ID_mask) {
        this.ID_mask = ID_mask;
    }

    public int generateID(Object context) {
        int ID = ID_mask | ID_reference;
        objectHashMap.put(ID, context);
        System.out.println("HELLO FROM ID GENERATE (" + ID_reference + ") !");
        ID_reference++;
        return ID;
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

        Object a = objectHashMap.remove(namesHashMap.get(name));
        Integer b =  namesHashMap.remove(name);

        System.out.println("@\tObject " + a.toString() + " removed");

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
