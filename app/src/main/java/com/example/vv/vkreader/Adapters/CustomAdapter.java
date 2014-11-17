package com.example.vv.vkreader.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vv.vkreader.R;

public class CustomAdapter extends ArrayAdapter{
    private static String[] title;
    private LayoutInflater inflater;
    private ViewHolder holder;

    public CustomAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2, title);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public static void setArray(String[] tit) {
        title = tit;
    }

    static class ViewHolder {
        public TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.option_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(title[position]);
        return view;
    }
}
