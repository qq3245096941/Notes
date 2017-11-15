package com.example.miaowenzhao.notes.uitls;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by miaowenzhao on 2017/11/9.
 */

public class Sp {
    public static void saveInt(Context context,String key,int value){
        SharedPreferences.Editor editor = context.getSharedPreferences("data",Context.MODE_PRIVATE).edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public static int readInt(Context context,String key,int defValue){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return preferences.getInt(key,defValue);
    }
}
