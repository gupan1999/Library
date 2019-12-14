package com.example.version1.Activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.version1.Model.AcademicDissertation;
import com.example.version1.R;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public class AcademicDissertations extends AppCompatActivity {
    private RecyclerView academicDissertationre;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private List<AcademicDissertation> academicDissertationList=new ArrayList<AcademicDissertation>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_dissertation);
        academicDissertationre=findViewById(R.id.academicdissertationre);
        AcademicDissertation academicDissertation=new AcademicDissertation("通信原理","专业课",R.mipmap.abcd);
        AcademicDissertation academicDissertation1=new AcademicDissertation("通信","专业课",R.mipmap.defg);
        academicDissertationList.add(academicDissertation);
        academicDissertationList.add(academicDissertation1);

        baseRecyclerAdapter=new BaseRecyclerAdapter<AcademicDissertation>(this,R.layout.academicdissertationitem,academicDissertationList){
            @Override
            public void convert(BaseViewHolder holder, AcademicDissertation academicDissertation) {

            }

            @Override
            public void setting(BaseViewHolder holder) {

            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        academicDissertationre.setLayoutManager(layoutManager);
        academicDissertationre.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); //item间的分割线
        academicDissertationre.setAdapter(baseRecyclerAdapter);
    }
}