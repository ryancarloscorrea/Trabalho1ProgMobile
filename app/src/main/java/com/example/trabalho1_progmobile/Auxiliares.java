package com.example.trabalho1_progmobile;

public class Auxiliares {
    public static boolean isNullText(String text) {
        if (text.trim().equals("") || text.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isInvalidNumber(int number) {
        if (number == 0){
            return true;
        }else{
            return false;
        }
    }
}
