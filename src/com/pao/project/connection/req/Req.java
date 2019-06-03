package com.pao.project.connection.req;

import java.io.*;

public class Req implements ReqConstants, Serializable {
    private String reqValue;

    public Req() { this(NONE); }

    public Req(String reqValue) {
        this.reqValue = okValue(reqValue);
    }

    public String getReqValue() {
        return reqValue;
    }

    public boolean isOk() {
        return reqValue.equals(ReqConstants.OK);
    }

    private String okValue(String str) {
        for (String val : All)
            if(str.equals(val)) return str;
        return NONE;
    }

}
