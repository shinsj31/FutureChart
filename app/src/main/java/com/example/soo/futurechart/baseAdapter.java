package com.example.soo.futurechart;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by soo on 2017-08-07.
 */

public class baseAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> data;
    ArrayList<String> names;
    LayoutInflater layoutInflater;

    public baseAdapter(Context context, ArrayList<Integer> data)
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View itemLayout=layoutInflater.inflate(R.layout.activity_list_item,null);
        final EditText editText=(EditText)itemLayout.findViewById(R.id.et_item);
        final EditText etName=(EditText)itemLayout.findViewById(R.id.et_item_name);
        Button btn=(Button)itemLayout.findViewById(R.id.btn_modify);

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                editText.setText("");
                return false;
            }
        });
        etName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                etName.setText("");
                return false;
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.set(i,Integer.parseInt(editText.getText().toString()));
                names.set(i,etName.getText().toString());
            }
        });

        editText.setText(data.get(i)+"");
        etName.setText(names.get(i));

        return itemLayout;
    }

    public void setNames(ArrayList<String> data_names){names=data_names;}
    public ArrayList<String> getNames(){return names;}
    public ArrayList<Integer> getData(){return data;}
}
