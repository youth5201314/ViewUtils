package com.youth.viewutils.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        des=getResources().getStringArray(R.array.des);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(new MainAdapter(this,des));
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                startActivity(new Intent(this, TextViewExpandableActivity.class)
                        .putExtra("des", des[i]));
                break;
            case 1:
                startActivity(new Intent(this, TextViewStyleActivity.class)
                        .putExtra("des", des[i]));
                break;
            case 2:
                startActivity(new Intent(this, JustifyTextViewActivity.class)
                        .putExtra("des", des[i]));
                break;
            case 3:
                startActivity(new Intent(this, CenterDrawableTextViewActivity.class)
                        .putExtra("des", des[i]));
                break;
        }
    }
}
