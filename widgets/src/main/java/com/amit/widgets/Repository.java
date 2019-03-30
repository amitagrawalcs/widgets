package com.amit.widgets;

public class Repository {

    public static boolean containsDigit(String s){
        if (s.contains("0")) return true;
        if (s.contains("1")) return true;
        if (s.contains("2")) return true;
        if (s.contains("3")) return true;
        if (s.contains("4")) return true;
        if (s.contains("5")) return true;
        if (s.contains("6")) return true;
        if (s.contains("7")) return true;
        if (s.contains("8")) return true;
        return s.contains("9");
    }
}
