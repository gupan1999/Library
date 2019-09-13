package com.example.version1.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.version1.MyApplication;
import com.example.version1.R;
import com.example.version1.Util.HttpUtil;
import com.example.version1.Util.Temp;
import com.example.version1.greendao.User;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.greendao.MessageInformation;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView recyclerView2;
    private SwipeRefreshLayout swipeRefresh1;
    private Handler handler;
    private  TextView message_nodata;
    private BaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_message);


        recyclerView2=findViewById(R.id.recyclerview2);
  //      if(User.mesList!=null) {          //如果列表数据不为空(此时要么在离线状态下从本地数据库得到了上一次保存的数据，要么成功从服务器得到新数据)
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
   //     }else{          //列表数据为空(此时可能网络错误，从服务器获取数据失败；可能服务器上没有相关数据，可能在离线状态下从数据库读取失败)
//            recyclerView2.setAdapter(adapter);
            message_nodata=findViewById(R.id.message_nodata);
            if (adapter.getItemCount()==0)message_nodata.setVisibility(View.VISIBLE);

  //      }
        swipeRefresh1 = findViewById(R.id.swipe_refresh1);
        swipeRefresh1.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLentInformation();
            }
        });
    }
    private void refreshLentInformation(){
                 handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what){
                        //加载网络成功进行UI的更新,处理得到的图片资源
                        case HttpUtil.SUCCESS:
                            adapter.updateItems(User.mesList);
                            checkNull();
                            adapter.notifyDataSetChanged();
                            swipeRefresh1.setRefreshing(false);
                            break;
                        //当加载网络失败执行的逻辑代码
                        case HttpUtil.FAIL:
                            Toast.makeText(MessageActivity.this, "网络出现了问题", Toast.LENGTH_SHORT).show();
                            DaoSession daoSession = GreenDaoManager.getInstance().getDaoSession();
                            User.mesList = daoSession.getMessageInformationDao().loadAll();         //从本地数据库 的相应表中拉取上一次保存的数据
                            System.out.println(User.mesList);
                            adapter.updateItems(User.mesList);
                            checkNull();
                            adapter.notifyDataSetChanged();
                            swipeRefresh1.setRefreshing(false);
                            break;
                    }

                }
            };
                 HttpUtil.getInformation(handler);

        }

    private void checkNull(){
        if (adapter.getItemCount()==0)message_nodata.setVisibility(View.VISIBLE);
        else message_nodata.setVisibility(View.GONE);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

            switch (item.getItemId()) {

                case R.id.delete:
                    Toast.makeText(MessageActivity.this, "删除成功",
                            Toast.LENGTH_SHORT).show();
                    MessageInformation removedMsg = Temp.removeRecyclerViewItem(adapter,User.mesList);   //得到被移除的数据对象
                    DaoSession daoSession= GreenDaoManager.getInstance().getDaoSession();
                    daoSession.getMessageInformationDao().delete(removedMsg);  //从数据库中删除对应数据
                    checkNull();
                    return true;
                case R.id.remove:
                    //Toast.makeText(MessageActivity.this,"remove",Toast.LENGTH_SHORT).show();
                    Temp.removeRecyclerViewItem(adapter,User.mesList);
                    return  true;
            }

        return false;
    }
}
