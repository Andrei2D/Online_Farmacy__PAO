package com.pao.project.actors;

import com.pao.project.manager.IDentity;
import com.pao.project.manager.Mask;

public class Client extends User {

    static {
        iDentity = new IDentity(Mask.Client.getMask());
    }

    /// Commands hystory
    public Client() {
        super();
    }

    public Client(String username, String password) {
        super(username, password);
    }
}
