package com.example.soo.futurechart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    baseAdapter adapter;
    ArrayList<Integer> data;
    ArrayList<String> data_name;

    EditText et_total;
    EditText et_start;
    EditText et_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data=new ArrayList<Integer>();
        data_name=new ArrayList<String>();
        adapter=new baseAdapter(this,data);

        et_total=(EditText)findViewById(R.id.et_total);
        et_start=(EditText)findViewById(R.id.et_startAge);
        et_end=(EditText)findViewById(R.id.et_endAge);

        listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    public void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_add:
            {
                data.add(0);
                data_name.add("item");
                adapter.setNames(data_name);
                adapter.notifyDataSetChanged();
                break;
            }
            case R.id.btn_delete:
            {
                if(data.size()>0)
                {
                    data_name.remove(data.size()-1);
                    data.remove(data.size()-1);
                    adapter.notifyDataSetChanged();
                }
                break;
            }
            case R.id.btn_enter:
            {
                data=adapter.getData();
                data_name=adapter.getNames();
                int total=Integer.parseInt(et_total.getText().toString());
                int start=Integer.parseInt(et_start.getText().toString());
                int end=Integer.parseInt(et_end.getText().toString());
                int[] items=new int[data.size()];

                for(int i=0; i<items.length; i++)
                    items[i]=data.get(i);


                Intent intent=new Intent(this,Gragh1Activity.class);
                intent.putExtra("total",total);
                intent.putExtra("start",start);
                intent.putExtra("end",end);
                intent.putExtra("items",items);
                intent.putExtra("names",data_name);

                startActivity(intent);
                break;
            }
        }
    }
}
