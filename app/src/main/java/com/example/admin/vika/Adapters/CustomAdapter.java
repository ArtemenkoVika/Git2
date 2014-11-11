package com.example.admin.vika.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.vika.R;

public class CustomAdapter extends BaseAdapter{
    private static String[] title;
    private LayoutInflater inflater;

    public CustomAdapter(Context context) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public String getItem(int position) {
        return title[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setArray(String[] title) {
        this.title = title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.row, null);
        TextView textView = (TextView) view.findViewById(R.id.option_text);
        textView.setText(title[position]);
        return view;
    }
}