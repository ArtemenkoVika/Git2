package com.example.admin.vika.Adapters;

import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vika.R;

public class CustomAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private static final String[] cats = {"Немецкий рекс", "Гавана",
            "Австралийская дымчатая кошка", "Американская жесткошерстная кошка"};
    private static final int[] images = {R.drawable.rex, R.drawable.havana,
            R.drawable.australian, R.drawable.american};

    public CustomAdapter(Context context) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cats.length;
    }

    @Override
    public String getItem(int position) {
        return cats[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.row, null);
        ImageView image = (ImageView) view.findViewById(R.id.option_icon);
        image.setImageResource(images[position]);
        TextView textView = (TextView) view.findViewById(R.id.option_text);
        textView.setText(cats[position]);
        return view;
    }
}