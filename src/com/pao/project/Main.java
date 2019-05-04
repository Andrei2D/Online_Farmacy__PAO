package com.pao.project;


import com.pao.project.products.types.Pills;
import com.pao.project.services.UserService;

import java.io.IOException;

public class Main {

    static UserService uServ = new UserService();
    public static void main(String[] args)  {
        try {
            uServ.readTest();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
