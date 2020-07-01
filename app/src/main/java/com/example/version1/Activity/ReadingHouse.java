package com.example.version1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.version1.Model.ReserveInfo;
import com.example.version1.R;
import com.example.version1.Util.BaseActivity;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.customed.CustomClickListener;

import java.util.ArrayList;

public class ReadingHouse extends BaseActivity {
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter<ReserveInfo> adapter;
    private ArrayList<ReserveInfo> reserveInfos = new ArrayList<ReserveInfo>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reading_house);
        recyclerView = findViewById(R.id.recyclerView5);


        ReserveInfo r1 =new ReserveInfo("朗读亭(东侧)","支持1-2人使用，位于一楼");
        ReserveInfo r2 =new ReserveInfo("朗读亭(西侧)","支持1-2人使用，位于一楼");
        reserveInfos.add(r1);
        reserveInfos.add(r2);
        adapter = new BaseRecyclerAdapter<ReserveInfo>(this,R.layout.reserve_item,reserveInfos) {
            @Override
            public void convert(BaseViewHolder holder, ReserveInfo reserveInfo) {
                holder.setText(R.id.reserve_name,reserveInfo.getReserveName());
                holder.setText(R.id.reserve_info,reserveInfo.getInfo());
            }

            @Override
            public void setting(final BaseViewHolder holder) {



                holder.getItemView().setOnClickListener(new CustomClickListener() {

                    @Override
                    protected void onSingleClick() {
                        Intent intent = new Intent(ReadingHouse.this, ChooseTimeActivity.class);
                        String name = adapter.getmDataByPosition(holder.getAdapterPosition()).getReserveName();
                        intent.putExtra("name",name);
                        startActivity(intent);
                    }

                    @Override
                    protected void onFastClick() {

                    }
                });
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView.setLayoutManager(layoutManager);  //设置为线性布局管理
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//每行划分割线
        recyclerView.setAdapter(adapter);
    }
}
