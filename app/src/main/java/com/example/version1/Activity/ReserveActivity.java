package com.example.version1.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;

import com.example.version1.R;

public class ReserveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_reserve);

        Button button1=findViewById(R.id.discussroom);
        Button button2=findViewById(R.id.seat);
        Button button3=findViewById(R.id.readinghouse);
        //TitleLayout titleLayout=findViewById(R.id.titleLayout5);
        //titleLayout.setTitle("预约");

    }
}
