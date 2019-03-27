package com.pao.project.actors;

public class Admin extends User{

    static {
        class_mask = 0b1011000000000000;
    }

    ///

    public Admin(String username, String password) {
        super(username, password);
    }

    // SPECIFIC METHODS FOR CHANGING PRODUCTS
}
