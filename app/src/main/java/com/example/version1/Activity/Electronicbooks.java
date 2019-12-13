package com.example.version1.Activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.version1.Model.Electronicbook;
import com.example.version1.R;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class Electronicbooks extends AppCompatActivity {
    private RecyclerView electronicbookre;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private List<Electronicbook> electronicbookList=new ArrayList<Electronicbook>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronicbooks);
        electronicbookre=findViewById(R.id.electronicbookre);
        Electronicbook electronicbook=new Electronicbook("通信原理","专业课",R.mipmap.abcd);
        Electronicbook electronicbook1=new Electronicbook("通信","专业课",R.mipmap.defg);
        electronicbookList.add(electronicbook);
        electronicbookList.add(electronicbook1);
        baseRecyclerAdapter=new BaseRecyclerAdapter<Electronicbook>(this,R.layout.electroincbookitem,electronicbookList) {
            @Override
            public void convert(BaseViewHolder holder, Electronicbook electronicbook) {
                holder.setText(R.id.textView9,electronicbook.getBookname());
                holder.setText(R.id.textView10,electronicbook.getDetails());
                holder.setImageResource(R.id.imageView2,electronicbook.getImageid());
            }

            @Override
            public void setting(BaseViewHolder holder) {

            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        electronicbookre.setLayoutManager(layoutManager);
        electronicbookre.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); //item间的分割线
        electronicbookre.setAdapter(baseRecyclerAdapter);
    }
}
