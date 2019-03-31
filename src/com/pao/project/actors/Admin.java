package com.pao.project.actors;

import com.pao.project.manager.IDentity;
import com.pao.project.manager.Mask;

public class Admin extends User{

    static {
        iDentity = new IDentity(Mask.Admin.getMask());
    }
    ///

    public Admin () {
        super();
    }

    public Admin(String username, String password) {
        super(username, password);
    }

    // SPECIFIC METHODS FOR CHANGING PRODUCTS
}
