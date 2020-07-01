package com.example.version1.Activity;


import android.os.Bundle;
import android.view.Window;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.version1.Model.Academic;
import com.example.version1.R;
import com.example.version1.Util.BaseActivity;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public class AcademicDissertations extends BaseActivity {
    private RecyclerView academicDissertationre;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    //private List<AcademicDissertation> academicDissertationList=new ArrayList<AcademicDissertation>();
    private List<Academic> academicList=new ArrayList<Academic>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_academic_dissertation);
        academicDissertationre=findViewById(R.id.academicdissertationre);
        //AcademicDissertation academicDissertation=new AcademicDissertation("散文江湖","形散神不散，优美娓娓来。散文，以其优美的文字，丰富的意向，舒缓的情调让人喜爱。或歌颂，或致意，或赞美...",R.mipmap.book2);
        //AcademicDissertation academicDissertation1=new AcademicDissertation("时间移民","选取25岁以下的人类成员向未来移民。旅行队伍进行了多次停留，但每一次的地球环境都不再适合人类居住...",R.mipmap.book1);
        Academic academic=new Academic("基于虚拟演播室的微课设计与开发——以《社会心理学》为例");
        Academic academic1=new Academic("基于人工智能(AI)技术的光通信网络:Hope还是Hype");
        Academic academic2=new Academic("边缘计算在采煤机控制系统中的应用");

        //academicDissertationList.add(academic);
       // academicDissertationList.add(academic1);
        academicList.add(academic);
        academicList.add(academic1);
        academicList.add(academic2);
        /*
        baseRecyclerAdapter=new BaseRecyclerAdapter<AcademicDissertation>(this,R.layout.academic_item,academicDissertationList){
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
        */
        baseRecyclerAdapter=new BaseRecyclerAdapter<Academic>(this,R.layout.academic_item,academicList){
            @Override
            public void convert(BaseViewHolder holder, Academic academic) {
               holder.setText(R.id.academictitle,academic.getTitle());
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