package com.pao.project.manager;

import java.util.ArrayList;
import java.util.HashMap;

public class FreshManager<T extends Manageable> {
    private ArrayList<T> elementsList;

    public void add(T elm) {
        elementsList.add(elm);
    }

    public FreshManager() {
        elementsList = new ArrayList<>();
    }

    public T get(Integer ID) {
        for (T elm: elementsList) {
            if(elm.getID().equals(ID))
                return elm;
        }
        return null;
    }

    public T get(String name) {
        for (T elm: elementsList) {
            System.out.println(name + " " + elm.getName() + "\t" + elm.getName().equals(name));
            if(elm.getName().equals(name))
                return elm;
        }
        return null;
    }

    public T remove(Integer index) {
        T toRemove = elementsList.get(index);
        elementsList.remove(index);
        return toRemove;
    }

    public T remove(String name) {
        T toRemove = null;
        for (int ind = 0; ind < elementsList.size(); ind++) {
            if(elementsList.get(ind).getName().equals(name)) {
                toRemove = elementsList.get(ind);
                elementsList.remove(ind);
                break;
            }
        }
        return toRemove;
    }

    public Integer size() {
        return elementsList.size();
    }

    public void output() {
        for (T elm: elementsList) {
            System.out.println(elm);
        }
        System.out.println();
    }
}
