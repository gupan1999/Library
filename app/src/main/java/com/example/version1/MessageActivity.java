package com.example.version1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.customed.TitleLayout;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.greendao.MessageInformation;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView recyclerView2;


    private BaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_message);

        TitleLayout titleLayout=findViewById(R.id.titleLayout2);
        titleLayout.setTitle("消息通知");
        recyclerView2=findViewById(R.id.recyclerview2);
        adapter= new BaseRecyclerAdapter<MessageInformation>(this,R.layout.messageitems,User.mesList) {
            @Override
            public void convert(BaseViewHolder holder, MessageInformation messageInformation) {
                holder.setText(R.id.message,messageInformation.getMessage());
                holder.setText(R.id.messageTime,messageInformation.getMessageTime());

            }
            public void setting(final BaseViewHolder holder){
                holder.setOnCreateContextMenuListener(holder.getItemView());        //每个ViewHolder设置监听
                holder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {                       //设置长按监听，只为获取选中的ViewHolder的位置
                        adapter.setPosition(holder.getAdapterPosition());
                        return false;
                    }
                });
            }

        };
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL)); //item间的分割线
        //MessageAdapter adapter=new MessageAdapter(mesList);
        recyclerView2.setAdapter(adapter);



    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {

            switch (item.getItemId()) {

                case R.id.delete:
                    Toast.makeText(MessageActivity.this, "delete",
                            Toast.LENGTH_SHORT).show();
                    MessageInformation removedMsg=User.mesList.remove(adapter.getPosition());    //移除数据源
                    adapter.notifyItemRemoved(adapter.getPosition());  //移除item
                    adapter.notifyItemRangeChanged(adapter.getPosition(),adapter.getItemCount());  //正确删除后的动画效果
                    DaoSession daoSession= GreenDaoManager.getInstance().getDaoSession();
                    daoSession.getMessageInformationDao().delete(removedMsg);

                default:
            }

        return super.onContextItemSelected(item);
    }
}
