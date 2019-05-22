package com.pao.project;

public enum CcStrings {
    FarmName ("Farmacia Perla-farm."),
    FarmCatchPhrase("Noi sa fim sanatosi !");

    String string;
    
    CcStrings(String string) {
        this.string = string;
    }
    
    public String getString() {
        return string;
    }
}
