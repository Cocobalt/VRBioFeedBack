package com.example.vrbiofeedback.Utils;

public class CheckString {


    public static boolean isEmpty(String checkstr){
        if (checkstr!=null&&checkstr.length()>0){
            checkstr = checkstr.trim().replace(" ","");
            return "null".equals(checkstr)||"NULL".equals(checkstr);
        }
        else{
            return true;
        }
    }
}
