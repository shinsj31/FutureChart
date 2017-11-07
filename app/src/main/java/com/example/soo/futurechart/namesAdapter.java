package com.example.soo.futurechart;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by soo on 2017-08-16.
 */

public class namesAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> data;
    LayoutInflater layoutInflater;

    public namesAdapter(Context context, ArrayList<String> data)
    {
        this.context=context;
        this.data=data;
        layoutInflater=LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() { return data.size(); }

    @Override
    public Object getItem(int position)  {
        return data.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemLayout=layoutInflater.inflate(R.layout.activity_item_names,null);
        Button btn=(Button)itemLayout.findViewById(R.id.btnName);
        btn.setText(data.get(i));

        return itemLayout;
    }
}
