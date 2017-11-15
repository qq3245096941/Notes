package com.example.miaowenzhao.notes.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.miaowenzhao.notes.R;
import com.example.miaowenzhao.notes.activity.ParticularsDiaryActivity;
import com.example.miaowenzhao.notes.entity.SqliteDiary;
import com.example.miaowenzhao.notes.uitls.Uitls;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by miaowenzhao on 2017/11/1.
 */

public class DiaryAdapter extends BaseAdapter {
    private List<SqliteDiary> list;
    private LayoutInflater inflater;
    private Activity context;
    private DiaryAdapter.ViewHolder viewHolder = new DiaryAdapter.ViewHolder();

    public void setList(List<SqliteDiary> list) {
        this.list = list;
    }

    public DiaryAdapter(Activity context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SqliteDiary getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final SqliteDiary diary = list.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.text_title = convertView.findViewById(R.id.text_title);
            viewHolder.text_time = convertView.findViewById(R.id.text_time);
            viewHolder.img_weather = convertView.findViewById(R.id.img_weather);
            viewHolder.img = convertView.findViewById(R.id.img);
            viewHolder.btn_delete = convertView.findViewById(R.id.btn_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text_title.setText(diary.getText_title());
        viewHolder.text_time.setText(diary.getText_time());
        viewHolder.img_weather.setImageResource(diary.getWeather_id());
        if (diary.getImgPath() != null) {
            viewHolder.img.setTag(R.id.image_tag, position);
            if ((int) viewHolder.img.getTag(R.id.image_tag) == position) {
                Glide.with(context)
                        .load(diary.getImgPath())
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.img);
                viewHolder.img.setTag(R.id.image_tag, position);

            }
        }

        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("确定要删除吗?");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        notifyDataSetChanged();
                        DataSupport.deleteAll(SqliteDiary.class, "text_title == ?", diary.getText_title());
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.create().dismiss();
                    }
                });
                builder.create().show();
            }
        });

        /*图片的点击事件*/
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ParticularsDiaryActivity.class);
                intent.putExtra("time", diary.getText_time());
                intent.putExtra("icon", diary.getWeather_id());
                intent.putExtra("body", diary.getText_body());
                intent.putExtra("title", diary.getText_title());
                intent.putExtra("imgPath", diary.getImgPath());
                intent.putExtra("color", diary.getColor());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView text_title;
        TextView text_time;
        ImageView img_weather, img;
        Button btn_delete;
    }

}
