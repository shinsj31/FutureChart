package com.example.soo.futurechart;

import android.content.Intent;
import android.graphics.Rect;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class Gragh1Activity extends AppCompatActivity {

    int total;
    int start;
    int end;
    int[] items;
    ArrayList<String> names;
    GraphView graphView;
    ListView listView;
    namesAdapter adapter;

    Rect[] rects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gragh1);

        Intent intent=getIntent();
        graphView=(GraphView)findViewById(R.id.graph);

        total=intent.getIntExtra("total",0);
        start=intent.getIntExtra("start",0);
        end=intent.getIntExtra("end",0);
        items=intent.getIntArrayExtra("items");
        names=intent.getStringArrayListExtra("names");

        adapter=new namesAdapter(this,names);
        listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),Graph2Activity.class);
                intent.putExtra("value",items[i]);
                intent.putExtra("size",end-start+1);
                intent.putExtra("age",start);
                startActivity(intent);
            }
        });

        graphView.setPoints(total, start, end-start+1, items, true,0);
        graphView.setNames(names);
        int size=graphView.getGap()*(end-start+1)+graphView.getGap();
        findViewById(R.id.graph_layout).setMinimumHeight(size);
        findViewById(R.id.view1).setMinimumHeight(size);
        graphView.setMinimumHeight(size);
        graphView.drawForBeforeDrawView();

        rects=graphView.getRect();

        graphView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                {
                    for(int i=0; i<rects.length; i++)
                    {
                        if(rects[i].contains((int)motionEvent.getX(),(int)motionEvent.getY())) {
                            Intent intent1=new Intent(getApplicationContext(),Graph3Activity.class);

                            intent1.putExtra("total",total);
                            intent1.putExtra("index",i);
                            intent1.putExtra("start",1);
                            intent1.putExtra("end",12);
                            intent1.putExtra("items",items);
                            intent1.putExtra("names",names);

                            startActivity(intent1);

                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    public void onClick(View view)
    {
        if(listView.getVisibility()==View.INVISIBLE)
            listView.setVisibility(View.VISIBLE);
        else
            listView.setVisibility(View.INVISIBLE);
        //Intent intent=new Intent(this,Graph2Activity.class);
        //intent.putExtra("extras",graphView.getExtra());
        //startActivity(intent);
    }
}
