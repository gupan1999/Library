package com.example.version1.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.version1.Model.Book;
import com.example.version1.Model.Collin;
import com.example.version1.MyApplication;
import com.example.version1.R;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.Util.HttpUtil;

public class DetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter adapter;
    private TextView detail_nodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        recyclerView=findViewById(R.id.recyclerView);
        detail_nodata=findViewById(R.id.detail_nodata);
        adapter = new BaseRecyclerAdapter<Collin>(this, R.layout.detailitem, HttpUtil.collinList) {
            @Override
            public void convert(BaseViewHolder holder, Collin collin) {
                holder.setText(R.id.dept, "馆藏部门:"+collin.getDept());
                holder.setText(R.id.status, "状态:"+collin.getStatus());
                holder.setText(R.id.callnumber,"索书号:"+collin.getCallnumber());
                holder.setText(R.id.shelfn,"架位号:"+collin.getShelfn());
                holder.setText(R.id.barcode,"条码号:"+collin.getBarcode());
                holder.setText(R.id.registnumber,"注册号:"+collin.getRegistnumber());
            }

            @Override
            public void setting(final BaseViewHolder holder) {

            }

        };
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView.setLayoutManager(layoutManager);  //设置为线性布局管理
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//每行划分割线
        recyclerView.setAdapter(adapter);
        detail_nodata=findViewById(R.id.detail_nodata);
        //   lent_nodata.setVisibility(View.VISIBLE);
        checkNull();
        //      }
    }
    private void checkNull(){
        if (adapter.getItemCount()==0)detail_nodata.setVisibility(View.VISIBLE);
        else detail_nodata.setVisibility(View.GONE);
    }
}
