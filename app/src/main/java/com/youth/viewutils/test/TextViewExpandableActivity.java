package com.youth.viewutils.test;

import android.os.Bundle;

import com.youth.viewutils.textview.ExpandTextView;

public class TextViewExpandableActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_expandable);
        ExpandTextView expandTextView1= (ExpandTextView) findViewById(R.id.tv_expand1);
        ExpandTextView expandTextView2= (ExpandTextView) findViewById(R.id.tv_expand2);
        expandTextView1.setText(getResources().getString(R.string.text1)+getResources().getString(R.string.text1));
        expandTextView2.setText(getResources().getString(R.string.text2)+getResources().getString(R.string.text2));
    }
}
