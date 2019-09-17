package com.example.version1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.version1.R;
import com.example.version1.customed.TitleLayout;

/*
public class MythingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.fragment_mythings);
        //TitleLayout titleLayout = findViewById(R.id.titleLayout4);
        //titleLayout.setTitle("我的");

        Button Reserve = findViewById(R.id.reserve);   //返回按钮

        //Log.d("更新数据库", LitePal.findAll(MessageInformation.class).toString());
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
                Intent intent3 = new Intent(MythingsActivity.this, SettingActivity.class);
                startActivity(intent3);
                break;
            default:
                Log.d("MythingsActivity", "Others");
        }
    }
}
*/