package com.pao.project.manager;

public enum Mask {
    User        (0b0001000000000000),       //1.0.0.0
    Admin       (0b0010000000000000),       //2.0.0.0
    Client      (0b0011000000000000),       //3.0.0.0
    Product     (0b0000000100000000),       //0.1.0.0
    Pills       (0b0000001000000000),       //0.2.0.0
    Ointment    (0b0000001100000000),       //0.3.0.0
    Naturist    (0b0000010000000000),       //0.4.0.0
    Supplement  (0b0000010100000000);        //0.5.0.0

    private final int mask;

    Mask(int theMask) {
        mask = theMask;
    }

    public int getMask() {
        return mask;
    }
}
