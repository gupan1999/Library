package com.example.version1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.version1.customed.TitleLayout;

public class MythingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_mythings);
        TitleLayout titleLayout = findViewById(R.id.titleLayout4);
        titleLayout.setTitle("我的");

        Button Reserve = findViewById(R.id.reserve);   //返回按钮
        Button MyLent = findViewById(R.id.imageButton9);   //我的借阅按钮
        Button Message = findViewById(R.id.imageButton13);  //消息按钮
        Message.setOnClickListener(this);
        Reserve.setOnClickListener(this);
        MyLent.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton13:
                Intent intent1 = new Intent(MythingsActivity.this, MessageActivity.class);
                startActivity(intent1);

                break;
            case R.id.imageButton9:
                Intent intent2 = new Intent(MythingsActivity.this, MyLentActivity.class);

                startActivity(intent2);
                break;
            case R.id.reserve:
                Intent intent = new Intent(MythingsActivity.this, ReserveActivity.class);
                startActivity(intent);
                break;
            case R.id.imageButton15:
                Intent intent3 = new Intent(MythingsActivity.this,SettingActivity.class);
                startActivity(intent3);
                break;
            default:
                Log.d("MythingsActivity", "Others");
        }
    }
}
