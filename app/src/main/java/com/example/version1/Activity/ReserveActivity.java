package com.example.version1.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.version1.R;
import com.example.version1.customed.TitleLayout;

public class ReserveActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_reserve);

        button1=findViewById(R.id.discussroom);
        button2=findViewById(R.id.seat);
        button3=findViewById(R.id.readinghouse);
        TitleLayout titleLayout=findViewById(R.id.titleLayout5);
        titleLayout.setTitle("预约");


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReserveActivity.this, Discussroom.class);
                startActivity(intent);
            }
        });
    }
}
