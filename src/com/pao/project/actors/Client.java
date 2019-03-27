package com.pao.project.actors;

public class Client extends User {

    static {
        class_mask = 0b0111000000000000;
    }

    /// Commands hystory

    public Client(String username, String password) {
        super(username, password);
    }
}
