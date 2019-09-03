package com.example.version1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;

import com.example.version1.Util.HttpUtil;


public class MainActivity extends AppCompatActivity {

   //首页底部导航栏
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_search:
                    Intent intent_search=new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent_search);
                    return true;
                case R.id.navigation_hot:
                    Intent intent_hot=new Intent(MainActivity.this,HotActivity.class);
                    //Intent intent_hot=new Intent(MainActivity.this, WebActivity.class);
                    startActivity(intent_hot);
                    return true;
                case R.id.navigation_categories:
                    Intent intent_categories=new Intent(MainActivity.this, CategoriesActivity.class);
                    startActivity(intent_categories);
                    return true;
                case R.id.navigation_mythings:
                    HttpUtil.getInformation();
                    Intent intent_mythings=new Intent(MainActivity.this, MythingsActivity.class);
                    startActivity(intent_mythings);
                    return true;
            }
          return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_main);   //关联layout/activity_main.xml的配置
        Button button=findViewById(R.id.button);   //关联id为button的按钮
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



}
