package com.example.version1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReserveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_reserve);

        Button button1=(Button)findViewById(R.id.imageButton10);
        Button button2=(Button)findViewById(R.id.imageButton12);
        Button button3=(Button)findViewById(R.id.imageButton14);
        TextView t=(TextView)findViewById(R.id.textView) ;
        t.setText("预约");
        ImageButton Return=(ImageButton)findViewById(R.id.imageButton);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReserveActivity.this.finish();
            }
        });
    }
}
