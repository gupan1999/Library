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
        AcademicDissertation academicDissertation=new AcademicDissertation("散文江湖","形散神不散，优美娓娓来。散文，以其优美的文字，丰富的意向，舒缓的情调让人喜爱。或歌颂，或致意，或赞美...",R.mipmap.book2);
        AcademicDissertation academicDissertation1=new AcademicDissertation("时间移民","选取25岁以下的人类成员向未来移民。旅行队伍进行了多次停留，但每一次的地球环境都不再适合人类居住...",R.mipmap.book1);
        academicDissertationList.add(academicDissertation);
        academicDissertationList.add(academicDissertation1);

        baseRecyclerAdapter=new BaseRecyclerAdapter<AcademicDissertation>(this,R.layout.academicdissertationitem,academicDissertationList){
            @Override
            public void convert(BaseViewHolder holder, AcademicDissertation academicDissertation) {
                holder.setText(R.id.textView5,academicDissertation.getBookname());
                holder.setText(R.id.textView6,academicDissertation.getDetails());
                holder.setImageResource(R.id.imageView3,academicDissertation.getImageid());
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