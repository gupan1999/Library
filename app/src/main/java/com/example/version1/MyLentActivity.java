package com.example.version1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyLentActivity extends AppCompatActivity {
private List<Information>appList=MainActivity.appList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_my_lent);

        TextView t=(TextView)findViewById(R.id.textView) ;
        t.setText("我的借阅");
        ImageButton Return=(ImageButton)findViewById(R.id.imageButton);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLentActivity.this.finish();
            }
        });
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        ItemAdapter adapter=new ItemAdapter(appList);
        recyclerView.setAdapter(adapter);
    }

}
