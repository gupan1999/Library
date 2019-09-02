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

public class MessageActivity extends AppCompatActivity {

    public List<MessageInformation>getMessageList(List<Information>inList){
        List<MessageInformation>mesList=new ArrayList<MessageInformation>();
        for(Information information:inList){

            MessageInformation messageInformation=information.getMessageInformation(information);
            if(messageInformation.getMessage()!=null&&messageInformation.getMessageTime()!=null) {
                Log.d("MessageInformation", messageInformation.getMessage());
                Log.d("MessageInformation", messageInformation.getMessageTime());
                mesList.add(messageInformation);
            }
        }
        return mesList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_message);
        ImageButton Return=(ImageButton)findViewById(R.id.Return); //返回按钮
        TextView t=(TextView)findViewById(R.id.title) ; //本页标题
        t.setText("消息通知");
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageActivity.this.finish();
            }
        });
        List<MessageInformation>mesList=getMessageList(MainActivity.appList);
        RecyclerView recyclerView2=(RecyclerView)findViewById(R.id.recyclerview2);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        MessageAdapter adapter=new MessageAdapter(mesList);
        recyclerView2.setAdapter(adapter);


    }

}
