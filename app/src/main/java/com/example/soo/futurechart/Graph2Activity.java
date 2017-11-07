package com.example.soo.futurechart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.soo.futurechart.R.layout.activity_event_list_item;

public class Graph2Activity extends AppCompatActivity {

    GraphView2 graphView2;
    EditText etYear;
    Button btnShow;

    Button btnAdd;
    Button btnSub;
    Button btnCheck;
    ListView listView;

    ArrayList<String> names;
    ArrayList<Integer> years;
    ArrayList<Integer> values;

    itemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph2);
        etYear=(EditText)findViewById(R.id.et_year);

        btnAdd=(Button)findViewById(R.id.btn_plus);
        btnSub=(Button)findViewById(R.id.btn_minus);
        btnCheck=(Button)findViewById(R.id.btn_check);
        listView=(ListView)findViewById(R.id.listView);

        names=new ArrayList<String>();
        years=new ArrayList<Integer>();
        values=new ArrayList<Integer>();

        adapter=new itemAdapter(this,years,values,names);
        listView.setAdapter(adapter);

        btnShow=(Button)findViewById(R.id.btn_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnAdd.getVisibility()==View.VISIBLE)
                {
                    btnAdd.setVisibility(View.INVISIBLE);
                    btnSub.setVisibility(View.INVISIBLE);
                    btnCheck.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                }
                else
                {
                    btnAdd.setVisibility(View.VISIBLE);
                    btnSub.setVisibility(View.VISIBLE);
                    btnCheck.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                }
            }
        });

        Intent intent= getIntent();

        graphView2=(GraphView2)findViewById(R.id.graph);

        int value=intent.getIntExtra("value",0);
        int size=intent.getIntExtra("size",0);
        int startAge=intent.getIntExtra("age",0);

        double unit=value/10;
        int width=graphView2.getGap()*size+graphView2.getGap();
        findViewById(R.id.graph_layout).setMinimumHeight(width);
        findViewById(R.id.view1).setMinimumWidth(width);
        graphView2.setMinimumWidth(width);

        graphView2.setValue(value,size,startAge);
        graphView2.drawForBeforeDrawView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        graphView2.settingBottom(btnShow.getHeight());
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_plus:
            {
                names.add("");
                values.add(0);
                years.add(0);
                adapter.notifyDataSetChanged();
                break;
            }
            case R.id.btn_minus:
            {
                if(names.size()>0)
                {
                    names.remove(names.size()-1);
                    values.remove(values.size()-1);
                    years.remove(years.size()-1);
                    adapter.notifyDataSetChanged();
                }
                break;
            }
            case R.id.btn_check:
            {
                if(adapter.values.size()==0)
                {
                    Toast.makeText(this,"아이템을 추가해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                int year=0;
                int value=0;
                graphView2.setZero();
                for(int i=0; i<adapter.values.size();i++)
                {
                    year=adapter.years.get(i);
                    value=adapter.values.get(i);
                    graphView2.valuesSetting(year,value,true);
                    graphView2.drawForBeforeDrawView();
                }
                break;
            }
        }
        /*
        int year=Integer.parseInt(etYear.getText().toString());

        graphView2.drawForBeforeDrawView();
        if(view.getId()==R.id.btn_plus)
            graphView2.valuesSetting(year, true);
        else
            graphView2.valuesSetting(year, false);

        view.requestFocus();*/
    }
}
