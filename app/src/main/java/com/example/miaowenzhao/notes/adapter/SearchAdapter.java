package com.example.miaowenzhao.notes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miaowenzhao.notes.R;

import java.util.ArrayList;

/**
 * Created by miaowenzhao on 2017/11/7.
 */

public class SearchAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private LayoutInflater inflater;
    public SearchAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text = null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.search_item,null);
            text = convertView.findViewById(R.id.text);
        }
        if (text != null) {
            text.setText(list.get(position));
        }

        return convertView;
    }
}
