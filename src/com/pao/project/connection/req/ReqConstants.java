package com.pao.project.connection.req;

public interface ReqConstants {
    String LIR = "lir";
    String CAR = "car";

    String HLO = "hello";
    String BYE = "bye";

    String SOR = "sor";

    String OK = "ok";
    String NK = "nk";

    String NONE = "";

    String[] All = {HLO, BYE, LIR, CAR, SOR, OK, NK};
}

/*
 ### Acronyms
 ---
 LIR - *Log In Request*
 CAR - *Create Account Request*

 HLO - *Hello. Start session*
 BYE - *Bye. End session*

 SOR - *Send Object Request*

 UD - *User Details*
 AD - *Account Details*

 OK - *Request confirmed*
 NK - *Not oK* / *Request Denied*
 */