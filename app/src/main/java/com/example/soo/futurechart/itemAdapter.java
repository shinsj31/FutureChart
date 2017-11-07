package com.example.soo.futurechart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by soo on 2017-09-10.
 */

public class itemAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> names;
    public ArrayList<Integer> years;
    public ArrayList<Integer> values;
    LayoutInflater layoutInflater;

    public itemAdapter(Context context, ArrayList<Integer> years, ArrayList<Integer> values, ArrayList<String> names)
    {
        this.context=context;
        this.years=years;
        this.values=values;
        this. names=names;
        layoutInflater=LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() { return years.size(); }

    @Override
    public Object getItem(int position)  {
        return years.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemLayout=layoutInflater.inflate(R.layout.activity_event_list_item,null);
        Button btn_check=(Button)itemLayout.findViewById(R.id.btn_check);

        final int index=i;

        final EditText et_names=(EditText)itemLayout.findViewById(R.id.et_name);
        final EditText et_years=(EditText)itemLayout.findViewById(R.id.et_year);
        final EditText et_value=(EditText)itemLayout.findViewById(R.id.et_value);

        if(names.size()>index)
        {
            et_names.setText(names.get(index));
            et_value.setText(values.get(index)+"");
            et_years.setText(years.get(index)+"");
        }

        et_years.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et_years.setText("");
                return false;
            }
        });
        et_value.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et_value.setText("");
                return false;
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                names.set(index,et_names.getText().toString());
                years.set(index,Integer.parseInt(et_years.getText().toString()));
                values.set(index,Integer.parseInt(et_value.getText().toString()));
            }
        });
        return itemLayout;
    }
}
