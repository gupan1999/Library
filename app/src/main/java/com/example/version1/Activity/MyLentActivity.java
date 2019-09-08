package com.example.version1.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.version1.R;
import com.example.version1.greendao.User;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.customed.TitleLayout;
import com.example.version1.greendao.LentInformation;

public class MyLentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_my_lent);
        TitleLayout titleLayout=findViewById(R.id.titleLayout3);
        titleLayout.setTitle("我的借阅");
        recyclerView=findViewById(R.id.recyclerview);

        //LentAdapter adapter=new LentAdapter(LentList);  //放入适配器
        if(User.leList!=null) {  //与MessageActivity相关部分完全类似
            adapter = new BaseRecyclerAdapter<LentInformation>(this, R.layout.items, User.leList) {
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
        }else{
            recyclerView.setAdapter(null);
            TextView lent_nodata=findViewById(R.id.lent_nodata);
            lent_nodata.setVisibility(View.VISIBLE);
        }
    }
}
