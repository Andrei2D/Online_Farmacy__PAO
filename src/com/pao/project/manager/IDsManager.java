package com.pao.project.manager;


import java.util.HashMap;
/**
 The goal of this class is for each class to have a manager of
 the objects that have been created of that type and to properly generate
 ID's relative to a mask that each class will have specific

*/
public class IDsManager {

    private int ID_reference = 1;
    private int ID_mask;

    private HashMap<Integer, Object> objectHashMap = new HashMap<Integer, Object>();
    private HashMap<String, Integer> namesHashMap = new HashMap<String, Integer>();

    public IDsManager(int ID_mask) {
        this.ID_mask = ID_mask;
    }

    public int generateID(Object context) {
        int ID = ID_mask | ID_reference;
        objectHashMap.put(ID, context);

        ID_reference ++;
        return ID;
    }

    public boolean setNameForID (String name, Integer ID) {
        if(!namesHashMap.containsKey(ID))
            return false;

        namesHashMap.put(name, ID);
        return true;
    }

    public void removeElement(Integer ID) {
        objectHashMap.remove(ID);
    }

    public void removeElement(String name) {
        objectHashMap.remove(namesHashMap.get(name));
        namesHashMap.remove(name);
    }

    public Object getElement(Integer ID) {
        return objectHashMap.get(ID);
    }

    public Object getElement(String username) {
        return objectHashMap.get(namesHashMap.get(username));
    }
}
