package com.pao.project.products;

import com.pao.project.manager.Manageable;

import java.util.Scanner;

public abstract class Product implements Manageable {

    static private int IDReference = 1;

    private int uniqID;
    private String name;
    private double price;
    private int receip_mask;  ///when an order is put, this mask gives a discount generated by the receip ID
    protected String description;

    /** This function returns the amount of data each class is passing
     * trough the String vector from one generation to the other
     *  The Product class writes 4 fields:
     *  *
     * */
    protected int nrOfData () {
        return 4;
    }

    private static int getID() {
        return IDReference++;
    }

    private static int getID(int anotherID) {
        if(anotherID >= IDReference) {
            updateIDRef(anotherID);
            return anotherID;
        } else
            return getID();
    }

    private static void updateIDRef(int anID) {
        IDReference = Integer.max(anID + 1, IDReference);
    }

    public String getName() { return name; }

    public double getPrice() {
        return price;
    }

    public double getPrice (int receip_ID) {

        double discount = (double)
                (((0b11111111 << 4) & receip_ID) | receip_mask);
        if(discount > 100) discount = 100;
        discount = 1 - discount /100;

        return discount * price;
    }

    /** Product data to store
     * data[0] - uniqID
     * data[1] - name
     * data[2] - price
     * data[3] - receip_mask
     * @return String array with corresponding data
     */
    @Override
    public String[] dataToStore() {

        String[] data = new String[nrOfData()];
        data[0] = String.valueOf(uniqID);
        data[1] = name;
        data[2] = String.valueOf(price);
        data[3] = String.valueOf(receip_mask);

        return data;
    }

    @Override
    public int getClassMask() {
        return PRODUCT;
    }

    public void fillTheRest(String[] data) {
        this.description = data[data.length -1];
    }

    public void setData(String[] data) {
        int parsedID = Integer.parseInt(data[0]);
        this.uniqID = getID(parsedID);
        this.name = data[1];
        this.price = Double.parseDouble(data[2]);
        this.receip_mask = Integer.parseInt(data[3]);
    }

    @Override
    public String toString() {
        String bigString = "";
        bigString += "ID:     " + uniqID + "\n";
        bigString += "Name:   " + name + "\n";
        bigString += "Price:  " + price + "\n";
        bigString += "Receip: " + Integer.toBinaryString( receip_mask ) + "\n";

        return bigString;
    }

    @Override
    public String[] inputData(Scanner fin) {
        String[] data = new String[nrOfData()];
        data[0] = String.valueOf(getID());
        fin.nextLine();
        System.out.print("Input the NAME:  ");
        data[1] = fin.nextLine();
        System.out.print("Input the PRICE: ");
        data[2] = fin.nextLine();
        System.out.print("Input the RECEIP: ");
        data[3] = fin.next();

        return data;
    }
}
