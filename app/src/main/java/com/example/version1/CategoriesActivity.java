package com.example.version1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.version1.customed.TitleLayout;


public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_categories);      //加载布局
        TitleLayout titleLayout = findViewById(R.id.titleLayout);
        titleLayout.setTitle("分类");
    }
}
