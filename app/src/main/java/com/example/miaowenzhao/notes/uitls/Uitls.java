package com.example.miaowenzhao.notes.uitls;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by miaowenzhao on 2017/10/31.
 */

public class Uitls {
    public static void showToast(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
