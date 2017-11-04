package com.example.miaowenzhao.notes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.miaowenzhao.notes.activity.CreateActivity;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fab_btn;
    /*数据源*/
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private Toolbar toolbar;
    private SlideAndDragListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
    }

    private void init() {
        fab_btn = (FloatingActionButton) findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (SlideAndDragListView) findViewById(R.id.list_view);
    }

    private void initView() {
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
        /*上传按钮*/
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cloud:

                        break;
                }
                return false;
            }
        });

        com.yydcdut.sdlv.Menu menu = new com.yydcdut.sdlv.Menu(true, 0);
        /*回收站*/
        menu.addItem(new com.yydcdut.sdlv.MenuItem.Builder()
                .setWidth(90)
                .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_delete_black_24dp))
                .setBackground(ContextCompat.getDrawable(this, R.color.colorAccent))
                .setDirection(com.yydcdut.sdlv.MenuItem.DIRECTION_RIGHT)
                .build());
        /*返回*/
        menu.addItem(new com.yydcdut.sdlv.MenuItem.Builder()
                .setWidth(90)
                .setDirection(com.yydcdut.sdlv.MenuItem.DIRECTION_RIGHT)
                .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_subdirectory_arrow_right_black_24dp))
                .setBackground(ContextCompat.getDrawable(this, R.color.green))
                .build());
        listView.setMenu(menu);
        /*点击按钮*/
        listView.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {
            @Override
            public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
                switch (direction) {
                    case com.yydcdut.sdlv.MenuItem.DIRECTION_RIGHT:
                        switch (buttonPosition) {
                            case 0:

                                break;
                            case 1:
                                return com.yydcdut.sdlv.Menu.ITEM_SCROLL_BACK;
                        }
                        break;
                }
                return com.yydcdut.sdlv.Menu.ITEM_SCROLL_BACK;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_btn:
                startActivity(new Intent(this, CreateActivity.class));
                break;
        }
    }
}
