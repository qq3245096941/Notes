package com.example.miaowenzhao.notes.activity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.miaowenzhao.notes.R;
import com.example.miaowenzhao.notes.uitls.Uitls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;


/**
 * Created by miaowenzhao on 2017/10/30.
 */

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CreateActivity";
    private Toolbar toolbar;
    /*插入图片*/
    private ImageView img;
    /*天气弹框*/
    private PopupWindow weatherWindow;
    /*记录天气点击情况*/
    private Boolean isClikc = false;
    private FloatingActionButton fab_btn;
    private Calendar calendar;
    private EditText edit_body;
    /*标记fab按钮点击*/
    private Boolean isFabBtn = false;
    /*天气类*/
    private View weaterView;
    private RadioGroup radio_weather;
    private RadioButton img_qing, img_duoyun, img_wu, img_yin, img_xiaoyu,
            img_zhongyu, img_leidian, img_feng, img_bingbao, img_yuxue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        init();
        initView();
    }

    private void init() {
        weaterView = LayoutInflater.from(this).inflate(R.layout.weather, null);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        /*天气弹框*/
        weatherWindow = new PopupWindow(weaterView,
                WindowManager.LayoutParams.WRAP_CONTENT
                , WindowManager.LayoutParams.WRAP_CONTENT);

        fab_btn = (FloatingActionButton) findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(this);
        edit_body = (EditText) findViewById(R.id.edit_body);

        radio_weather = weaterView.findViewById(R.id.radio_weather);

        img_qing = weaterView.findViewById(R.id.img_qing);
        img_duoyun = weaterView.findViewById(R.id.img_duoyun);
        img_wu = weaterView.findViewById(R.id.img_wu);
        img_yin = weaterView.findViewById(R.id.img_yin);
        img_xiaoyu = weaterView.findViewById(R.id.img_xiaoyu);
        img_zhongyu = weaterView.findViewById(R.id.img_zhongyu);
        img_leidian = weaterView.findViewById(R.id.img_leidian);
        img_feng = weaterView.findViewById(R.id.img_feng);
        img_bingbao = weaterView.findViewById(R.id.img_bingbao);
        img_yuxue = weaterView.findViewById(R.id.img_yuxue);

        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(this);
    }

    private void initView() {
        /*填充日期*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            toolbar.setTitle(sdf.format(calendar.getTime()));


        }
        /*标题与活动绑定*/
        setSupportActionBar(toolbar);
        /*导航按钮*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateActivity.this.finish();
            }
        });
        /*保存按钮*/
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save_btn:
                        Uitls.showToast(CreateActivity.this, "保存");
                        break;
                }
                return false;
            }
        });

        radio_weather.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.img_qing:
                        setWeather(R.drawable.ic_qing, R.id.img_qing);
                        break;
                    case R.id.img_duoyun:
                        setWeather(R.drawable.ic_duo_yun, R.id.img_duoyun);
                        break;
                    case R.id.img_wu:
                        setWeather(R.drawable.ic_wu, R.id.img_wu);
                        break;
                    case R.id.img_yin:
                        setWeather(R.drawable.ic_yin, R.id.img_yin);
                        break;
                    case R.id.img_xiaoyu:
                        setWeather(R.drawable.ic_xiao_yu, R.id.img_xiaoyu);
                        break;
                    case R.id.img_zhongyu:
                        setWeather(R.drawable.ic_zhong_yu, R.id.img_zhongyu);
                        break;
                    case R.id.img_feng:
                        setWeather(R.drawable.ic_feng, R.id.img_feng);
                        break;
                    case R.id.img_bingbao:
                        setWeather(R.drawable.ic_bing_bao, R.id.img_bingbao);
                        break;
                    case R.id.img_yuxue:
                        setWeather(R.drawable.ic_yu_xue, R.id.img_yuxue);
                        break;
                    case R.id.img_leidian:
                        setWeather(R.drawable.ic_lei_dian, R.id.img_leidian);
                        break;

                }
            }
        });

    }

    private void setWeather(int drawable, int saveId) {
        fab_btn.setImageDrawable(ContextCompat.getDrawable(this, drawable));
        weatherWindow.dismiss();
        isClikc = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /*浮空按钮*/
            case R.id.fab_btn:
                if (!isClikc) {
                    weatherWindow.showAsDropDown(v);
                    isClikc = true;
                } else {
                    weatherWindow.dismiss();
                    isClikc = false;
                }
                break;
            case R.id.img:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == 1) {
                    Uri uri = data.getData();
                    Glide.with(this).load(uri).into(img);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }
}
