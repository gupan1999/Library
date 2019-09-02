package com.example.version1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyLentActivity extends AppCompatActivity {
//private List<Information>appList=MainActivity.appList;
public List<LentInformation>getLentList(List<Information>inList){
    List<LentInformation>LentList=new ArrayList<LentInformation>();
    for(Information information:inList){
        LentInformation lentInformation=information.getLentInformation(information);
        if(lentInformation.getBookName()!=null&&lentInformation.getLentTime()!=null) {
            Log.d("LentInformation", lentInformation.getBookName());
            Log.d("LentInformation", lentInformation.getLentTime());
            LentList.add(lentInformation);
        }
    }
    return LentList;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_my_lent);

        TextView t=(TextView)findViewById(R.id.title) ;
        t.setText("我的借阅");
        ImageButton Return=(ImageButton)findViewById(R.id.Return);  //注册返回按钮
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLentActivity.this.finish();
            }
        });  //返回事件监听
        List<LentInformation>LentList=getLentList(MainActivity.appList); //得到借阅信息
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView.setLayoutManager(layoutManager);  //设置为线性布局管理
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//每行划分割线
        LentAdapter adapter=new LentAdapter(LentList);  //放入适配器
        recyclerView.setAdapter(adapter);
    }

}
