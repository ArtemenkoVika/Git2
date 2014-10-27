package com.example.artemenkovika.myapplication;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;

public class Fragment2 extends ListFragment {
    private static final String data[] = new String[]{"First Screen", "Second Screen", "Change The Picture"};
    private TextView text;
    public static ListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_single_choice, data);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        text = (TextView) getActivity().findViewById(R.id.textView1);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String selectedItem = getListView().getItemAtPosition(position).toString();
        text.setText(selectedItem);
    }
}
