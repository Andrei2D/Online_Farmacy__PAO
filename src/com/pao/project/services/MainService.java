package com.pao.project.services;

import com.pao.project.connection.req.ReqConstants;
import com.pao.project.manager.FreshManager;
import com.pao.project.manager.Manageable;
import com.pao.project.manager.ProductCodes;

import java.util.HashMap;

public class MainService implements ProductCodes, ReqConstants {
    public HashMap<Integer, FreshManager> manager;
}
