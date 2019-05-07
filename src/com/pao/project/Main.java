package com.pao.project;


import com.pao.project.manager.Manageable;
import com.pao.project.products.types.Pills;
import com.pao.project.services.UserService;

import java.awt.font.FontRenderContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

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
