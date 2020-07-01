package com.example.version1.Activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.version1.Model.MyReserve;
import com.example.version1.R;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.Util.HttpUtil;
import com.example.version1.customed.TitleLayout;
import com.example.version1.manager.HttpManager;

import java.util.ArrayList;

public class DealActivity extends AppCompatActivity {
    private RecyclerView dealView;
    private BaseRecyclerAdapter<MyReserve>recyclerAdapter;
    private ArrayList<MyReserve>myReserves = new ArrayList<>();
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_deal);
        TitleLayout titleLayout = findViewById(R.id.titleLayout9);
        titleLayout.setTitle("预约记录");
        dealView = findViewById(R.id.dealView);
        progressBar= findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        HttpUtil.getMyReserves(new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (resultJson != null) {
                    JSONObject response = JSON.parseObject(resultJson);
                    JSONArray list = response.getJSONArray("[]");
                    if (list == null) {
                        list = new JSONArray();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject listItem = list.getJSONObject(i);
                        if (listItem == null) {
                            continue;
                        }
                        System.out.println("\nlistItem = list[" + i + "] = \n" + listItem + "\n\n");

                        MyReserve reserve = listItem.getObject("Reserve", MyReserve.class);
                        if (reserve == null) {
                            reserve = new MyReserve();
                        }
                        String name = reserve.getName();
                        System.out.println("reserve.name = " + name);
                        String start = reserve.getStart();
                        System.out.println("reserve.start = " + start);
                        String end = reserve.getEnd();
                        System.out.println("reserve.end = " + end);
                        reserve.setStart(start.replace(".0", ""));
                        reserve.setEnd(end.replace(".0", ""));
                        myReserves.add(reserve);
                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerAdapter.notifyDataSetChanged();
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DealActivity.this, "服务器连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerAdapter = new BaseRecyclerAdapter<MyReserve>(DealActivity.this,R.layout.my_reserves,myReserves) {

            @Override
            public void convert(BaseViewHolder holder, MyReserve myReserve) {
                holder.setText(R.id.textView37,"id: "+String.valueOf(myReserve.getId()));
                holder.setText(R.id.name,"地点: "+myReserve.getName());
                holder.setText(R.id.textView36,String.format("时间段: %s  -  %s",myReserve.getStart(),myReserve.getEnd()));
            }
            @Override
            public void setting(final BaseViewHolder holder) {
                holder.setOnCreateContextMenuListener(holder.getItemView());        //每个ViewHolder设置监听
                holder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {                       //设置长按监听，只为获取选中的ViewHolder的位置
                        recyclerAdapter.setPosition(holder.getAdapterPosition());
                        return false;
                    }
                });
            }
        };

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
        dealView.setLayoutManager(layoutManager);  //设置为线性布局管理
        dealView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//每行划分割线
        dealView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.cancel, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
            if(item.getItemId()==R.id.cancel) {
                final int position = recyclerAdapter.getPosition();
                MyReserve myReserve = recyclerAdapter.getmDataByPosition(position);
                HttpUtil.deleteReserve(myReserve.getId(), new HttpManager.OnHttpResponseListener() {
                    @Override
                    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                        if (resultJson!=null) {
                            JSONObject response = JSON.parseObject(resultJson);
                            JSONObject reserve = response.getJSONObject("reserve");
                            if (reserve == null) {
                                reserve = new JSONObject();
                            }
                            int code = reserve.getIntValue("code");
                            System.out.println("reserve.code = " + code);
                            String msg = reserve.getString("msg");
                            System.out.println("reserve.msg = " + msg);
                            long id = reserve.getLongValue("id");
                            System.out.println("reserve.id = " + id);
                            int count = reserve.getIntValue("count");
                            System.out.println("reserve.count = " + count);
                            if (code == 200) {
                                myReserves.remove(position);
                                recyclerAdapter.notifyItemChanged(position);
                                recyclerAdapter.notifyItemRangeChanged(recyclerAdapter.getPosition(), recyclerAdapter.getItemCount());  //正确删除后的动画效果
                                Toast.makeText(DealActivity.this, "成功取消预约",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DealActivity.this, "取消预约失败",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(DealActivity.this, "服务器连接失败，请稍后再试",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myReserves.remove(position);
                recyclerAdapter.notifyItemChanged(position);
                recyclerAdapter.notifyItemRangeChanged(recyclerAdapter.getPosition(),recyclerAdapter.getItemCount());  //正确删除后的动画效果
                Toast.makeText(DealActivity.this, "成功取消预约",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        return false;
    }
}