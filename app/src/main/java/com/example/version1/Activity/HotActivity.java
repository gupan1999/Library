package com.example.version1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.version1.R;
import com.example.version1.customed.TitleLayout;

//热门页面
public class HotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_hot);
        TitleLayout titleLayout=findViewById(R.id.titleLayout1);
        titleLayout.setTitle("热门");

    }
}
