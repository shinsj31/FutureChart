package com.example.soo.futurechart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Graph3Activity extends AppCompatActivity {

    int total;
    int start;
    int end;
    int[] items;
    int index;
    ArrayList<String> names;
    GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph3);

        Intent intent=getIntent();
        graphView=(GraphView)findViewById(R.id.graph);

        total=intent.getIntExtra("total",0);
        start=intent.getIntExtra("start",0);
        end=intent.getIntExtra("end",0);
        items=intent.getIntArrayExtra("items");
        names=intent.getStringArrayListExtra("names");

        index=intent.getIntExtra("index",0);

        graphView.setPoints(total, start, end-start+1, items, false,index);
        graphView.setNames(names);
        int size=graphView.getGap()*(end-start+1)+graphView.getGap();
        findViewById(R.id.graph_layout).setMinimumHeight(size);
        findViewById(R.id.view1).setMinimumHeight(size);
        graphView.setMinimumHeight(size);
        graphView.drawForBeforeDrawView();
    }
}
