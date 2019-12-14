package com.example.version1.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.version1.Model.Electronicbook;
import com.example.version1.R;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.Util.HttpUtil;
import com.example.version1.Model.LentInformation;
import com.example.version1.Model.User;

public class MyLentActivity extends AppCompatActivity {
    private Handler handler;
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private TextView lent_nodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_my_lent);
        recyclerView=findViewById(R.id.recyclerview);
            adapter = new BaseRecyclerAdapter<LentInformation>(this, R.layout.items, User.leList) {
                @Override
                public void convert(BaseViewHolder holder, Electronicbook electronicbook) {

                }

                @Override
                public void convert(BaseViewHolder holder, LentInformation lentInformation) {
                    holder.setText(R.id.bookName, lentInformation.getBookName());
                    holder.setText(R.id.lentTime, lentInformation.getLentTime());
                }

                @Override
                public void setting(BaseViewHolder holder) {

                }

            };
            LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
            recyclerView.setLayoutManager(layoutManager);  //设置为线性布局管理
            recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//每行划分割线
            recyclerView.setAdapter(adapter);
            lent_nodata=findViewById(R.id.lent_nodata);
            checkNull();

        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.
                OnRefreshListener() {
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
                        adapter.updateItems(User.leList);
                        checkNull();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                        break;
                    //当加载网络失败执行的逻辑代码
                    case HttpUtil.FAIL:
                        Toast.makeText(MyLentActivity.this, "网络出现了问题", Toast.LENGTH_SHORT).show();
                        checkNull();
                        swipeRefresh.setRefreshing(false);
                        break;
                }

            }
        };
        HttpUtil.getInformation(handler);
    }

    private void checkNull(){
        if (adapter.getItemCount()==0)lent_nodata.setVisibility(View.VISIBLE);
        else lent_nodata.setVisibility(View.GONE);
    }
    }



