package com.example.version1.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.version1.R;
import com.example.version1.greendao.User;
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
        if(User.mesList!=null) {          //如果列表数据不为空(此时要么在离线状态下从本地数据库得到了上一次保存的数据，要么成功从服务器得到新数据)
            adapter = new BaseRecyclerAdapter<MessageInformation>(this, R.layout.messageitems, User.mesList) {   //通过匿名内部类扩展通用adapter
                @Override                           //实现抽象方法convert和setting
                public void convert(BaseViewHolder holder, MessageInformation messageInformation) {
                    holder.setText(R.id.message, messageInformation.getMessage());
                    holder.setText(R.id.messageTime, messageInformation.getMessageTime());

                }

                public void setting(final BaseViewHolder holder) {
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

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);//线性布局管理Recyclerview
            recyclerView2.setLayoutManager(layoutManager);
            recyclerView2.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); //item间的分割线
            recyclerView2.setAdapter(adapter);
        }else{          //列表数据为空(此时可能网络错误，从服务器获取数据失败；可能服务器上没有相关数据，可能在离线状态下从数据库读取失败)
            recyclerView2.setAdapter(null);
            TextView message_nodata=findViewById(R.id.message_nodata);
            message_nodata.setVisibility(View.VISIBLE);      //默认状态下设置为Gone的文字显示
        }


    }
    private MessageInformation removeRecyclerViewItem(){
        MessageInformation removedMsg=User.mesList.remove(adapter.getPosition());    //移除数据源
        adapter.notifyItemRemoved(adapter.getPosition());  //移除item
        adapter.notifyItemRangeChanged(adapter.getPosition(),adapter.getItemCount());  //正确删除后的动画效果
        return  removedMsg;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

            switch (item.getItemId()) {

                case R.id.delete:
                    Toast.makeText(MessageActivity.this, "delete",
                            Toast.LENGTH_SHORT).show();
                    MessageInformation removedMsg = removeRecyclerViewItem();   //得到被移除的数据对象
                    DaoSession daoSession= GreenDaoManager.getInstance().getDaoSession();
                    daoSession.getMessageInformationDao().delete(removedMsg);  //从数据库中删除对应数据
                    return true;
                case R.id.remove:
                    Toast.makeText(MessageActivity.this,"remove",Toast.LENGTH_SHORT).show();
                    removeRecyclerViewItem();
                    return  true;
            }

        return false;
    }
}
