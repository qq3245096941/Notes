package com.example.miaowenzhao.notes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;

import com.example.miaowenzhao.notes.MainActivity;
import com.example.miaowenzhao.notes.R;
import com.example.miaowenzhao.notes.adapter.ThemeAdapter;
import com.example.miaowenzhao.notes.uitls.Key;
import com.example.miaowenzhao.notes.uitls.Sp;
import com.example.miaowenzhao.notes.uitls.Uitls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miaowenzhao on 2017/11/8.
 */

public class ThemeActivity extends AppCompatActivity {
    /*toolbar*/
    private Toolbar toolbar;
    private static final String TAG = "ThemeActivity";
    private GridView gridView;
    /*适配器*/
    private ThemeAdapter adapter;
    /*图片源*/
    private int[] img = new int[]{R.drawable.img_0
            , R.drawable.img_1
            , R.drawable.img_2
            , R.drawable.img_3
    };
    /*标题源*/
    private String[] img_title = new String[]{"钢铁", "晚霞", "黎明", "夜色"};

    private ArrayList<Map<String, Object>> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Sp.readInt(this,Key.ThemeKey,R.style.MyTheme_0));
        setContentView(R.layout.activity_theme);
        init();
        initView();
    }

    private void init() {
        gridView = (GridView) findViewById(R.id.gridview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initView() {
        /*添加数组元素到集合里面*/
        for (int i = 0; i < img.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", img[i]);
            map.put("img_title", img_title[i]);
            list.add(map);
        }
        /*设置适配器*/
        Log.d(TAG, list.toString());
        adapter = new ThemeAdapter(list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<CheckBox> list = new ArrayList<>();
                /*把每个item的checkBox都放在集合里面*/
                for (int i = 0; i < parent.getCount(); i++) {
                    CheckBox checkBox = gridView.getChildAt(i).findViewById(R.id.check_box);
                    list.add(checkBox);
                }
                switch (position) {
                    case 0:
                        setCheckIsClick(R.style.MyTheme_0, list, position);

                        break;
                    case 1:
                        setCheckIsClick(R.style.MyTheme_1, list, position);

                        break;
                    case 2:
                        setCheckIsClick(R.style.MyTheme_2, list, position);
                        break;
                    case 3:
                        setCheckIsClick(R.style.MyTheme_3, list, position);
                        break;
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemeActivity.this, MainActivity.class));
                ThemeActivity.this.finish();
            }
        });
    }

    private void setCheckIsClick(int themeId, ArrayList<CheckBox> list, int position) {
        Sp.saveInt(ThemeActivity.this, Key.ThemeKey, themeId);
        /*遍历集合中的checkBox，把每个都设置为false*/
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(false);
        }
        list.get(position).setChecked(true);
        Sp.saveInt(this,Key.GRAD_VIEW_POSITION,position);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getKeyCode()){
            case KeyEvent.KEYCODE_BACK:
                startActivity(new Intent(this,MainActivity.class));
                ThemeActivity.this.finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
