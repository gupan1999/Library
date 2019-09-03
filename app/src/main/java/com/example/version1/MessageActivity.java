package com.example.version1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.example.version1.customed.TitleLayout;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView recyclerView2;
    private List<MessageInformation>mesList=new ArrayList<MessageInformation>();
    private BaseRecyclerAdapter adapter;
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

        TitleLayout titleLayout=findViewById(R.id.titleLayout2);
        titleLayout.setTitle("消息通知");

        mesList=getMessageList(HttpUtil.informationList);
        recyclerView2=findViewById(R.id.recyclerview2);
        adapter= new BaseRecyclerAdapter<MessageInformation>(this,R.layout.messageitems,mesList) {
            @Override
            public void convert(BaseViewHolder holder, MessageInformation messageInformation) {
                holder.setText(R.id.message,messageInformation.message);
                holder.setText(R.id.messageTime,messageInformation.messageTime);
            }

        };
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //MessageAdapter adapter=new MessageAdapter(mesList);
        recyclerView2.setAdapter(adapter);


    }

}
