package com.example.version1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_reserve);

        button1 = findViewById(R.id.discussroom);
        button2 = findViewById(R.id.readinghouse);
        button3 = findViewById(R.id.seat);

        TitleLayout titleLayout = findViewById(R.id.titleLayout5);
        titleLayout.setTitle("预约");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReserveActivity.this, Discussroom.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReserveActivity.this, ReadingHouse.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReserveActivity.this,Seat.class);
                startActivity(intent);
            }
        });

    }


}