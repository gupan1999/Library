package com.example.version1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class HotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_hot);
        ImageButton Return = (ImageButton) findViewById(R.id.Return);  //返回按钮注册
        TextView t=(TextView)findViewById(R.id.title) ;   //title控件注册
        t.setText("热门"); //title名
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            HotActivity.this.finish();
            }
        });
    }
}
