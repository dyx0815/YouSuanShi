package com.dan.yousuanshi.utils;

import android.os.Handler;

public class MyHanlder{

    private static Handler hanlder = null;

    private MyHanlder(){

    }

    public static Handler getInstance(){
        if(hanlder == null){
            hanlder = new Handler();
        }
        return hanlder;
    }
}
