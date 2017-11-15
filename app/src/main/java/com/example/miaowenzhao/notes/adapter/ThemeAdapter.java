package com.example.miaowenzhao.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miaowenzhao.notes.R;
import com.example.miaowenzhao.notes.uitls.Key;
import com.example.miaowenzhao.notes.uitls.Sp;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by miaowenzhao on 2017/11/10.
 */

public class ThemeAdapter extends BaseAdapter {
    private ArrayList<Map<String, Object>> list;

    public ThemeAdapter(ArrayList<Map<String, Object>> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Map getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map<String, Object> map = list.get(position);
        ImageView imageView = null;
        TextView img_title = null;
        CheckBox checkBox = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.theme_item, null);
            imageView = convertView.findViewById(R.id.img);
            img_title = convertView.findViewById(R.id.img_title);
            checkBox = convertView.findViewById(R.id.check_box);
        }

        if (imageView != null) {
            imageView.setImageResource((int) map.get("img"));
            img_title.setText((String) map.get("img_title"));
            if (position == Sp.readInt(parent.getContext(), Key.GRAD_VIEW_POSITION, 0)) {
                checkBox.setChecked(true);
            }
        }

        return convertView;
    }
}
