package com.example.miaowenzhao.notes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.miaowenzhao.notes.R;
import com.example.miaowenzhao.notes.adapter.SearchAdapter;
import com.example.miaowenzhao.notes.entity.SqliteDiary;
import com.example.miaowenzhao.notes.uitls.Key;
import com.example.miaowenzhao.notes.uitls.Sp;
import com.example.miaowenzhao.notes.uitls.Uitls;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaowenzhao on 2017/11/7.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SearchActivity";
    private List<SqliteDiary> list;
    private ArrayList<String> title_list = new ArrayList<>();
    private SearchAdapter adapter;
    private View search_view;
    private ListView search_listview;
    private ArrayList<String> title_list2 = new ArrayList<>();
    private PopupWindow popupWindow;
    /*退出按钮*/
    private ImageView img_quit;
    /*搜索框*/
    private EditText edit_title;
    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Sp.readInt(this, Key.ThemeKey,R.style.MyTheme_0));
        setContentView(R.layout.activity_search);
        init();
        initView();

    }

    private void init() {
        list = DataSupport.findAll(SqliteDiary.class);
        /*初始化适配器*/
        adapter = new SearchAdapter(this);
        search_view = LayoutInflater.from(this).inflate(R.layout.search_view, null);
        search_listview = search_view.findViewById(R.id.search_listView);
        img_quit = (ImageView) findViewById(R.id.img_quit);
        img_quit.setOnClickListener(this);
        edit_title = (EditText) findViewById(R.id.edit_title);
        popupWindow = new PopupWindow(search_view
                , WindowManager.LayoutParams.WRAP_CONTENT
                , WindowManager.LayoutParams.WRAP_CONTENT);
        text = (TextView) findViewById(R.id.text);
    }

    private void initView() {
        Uitls.setTypeface(text,getAssets());
        /*得到标题数组*/
        for (int i = 0; i < list.size(); i++) {
            SqliteDiary diary = list.get(i);
            title_list.add(diary.getText_title());
        }
        Log.d(TAG, title_list.toString());

        edit_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                popupWindow.showAsDropDown(edit_title);
                title_list2.clear();
                for (int i = 0; i < title_list.size(); i++) {

                    String title = title_list.get(i);
                    if (title.contains(s)) {
                        title_list2.add(title);
                    }
                    Log.d(TAG, title_list2.toString());
                }

                adapter.setList(title_list2);
                search_listview.setAdapter(adapter);
                if (s.length() == 0) {
                    popupWindow.dismiss();
                }
            }
        });

        search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SqliteDiary diary = null;
                String title = (String) parent.getItemAtPosition(position);
                for(int i=0;i<list.size();i++){
                    if(title.equals(list.get(position).getText_title())){
                        diary = list.get(position);
                        break;
                    }
                }
                Intent intent = new Intent(SearchActivity.this,ParticularsDiaryActivity.class);
                if (diary != null) {
                    intent.putExtra("time", diary.getText_time());
                    intent.putExtra("icon", diary.getWeather_id());
                    intent.putExtra("body", diary.getText_body());
                    intent.putExtra("title", diary.getText_title());
                    intent.putExtra("imgPath", diary.getImgPath());
                }
                startActivity(intent);
                SearchActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_quit:
                finish();
                break;
        }
    }
}
