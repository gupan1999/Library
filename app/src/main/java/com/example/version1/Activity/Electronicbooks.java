package com.example.version1.Activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

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
        //getSupportActionBar().hide();
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_electronicbooks);

        electronicbookre=findViewById(R.id.electronicbookre);
        Electronicbook electronicbook=new Electronicbook("散文江湖","形散神不散，优美娓娓来。散文，以其优美的文字，丰富的意向，舒缓的情调让人喜爱。或歌颂，或致意，或赞美...",R.mipmap.book2);
        Electronicbook electronicbook1=new Electronicbook("时间移民","选取25岁以下的人类成员向未来移民。旅行队伍进行了多次停留，但每一次的地球环境都不再适合人类居住...",R.mipmap.book1);
        Electronicbook electronicbook2=new Electronicbook("深度学习","《深度学习》由全球知名的三位专家Ian Goodfellow、Yoshua Bengio 和Aaron Courville撰写，是深度学习领域奠基性的经典教材。全书的内容包括3个部分：第1部分介绍基本的数学工具和机器学习的概念，它们是深度学习的预备知识；第2部分系统深入地讲解现今已成熟的深度学习方法和技术；第3部分讨论某些具有前瞻性的方向和想法，它们被公认为是深度学习未来的研究重点。\n" +
                "《深度学习》适合各类读者阅读，包括相关专业的大学生或研究生，以及不具有机器学习或统计背景、但是想要快速补充深度学习知识，以便在实际产品或平台中应用的软件工程师。\n",R.mipmap.book3);
        Electronicbook electronicbook3=new Electronicbook("毛泽东选集(第一卷)","《毛泽东选集》(第1卷)包括了毛泽东同志在中国革命各个时期中的重要著作。几年前各地方曾经出过几种不同版本的《毛泽东选集》，都是没有经过著者审查的，体例颇为杂乱，文字亦有错讹，有些重要的著作又没有收过去。现在的这部选集，是按照中国共产党成立后所经历的各个历史时期并且按照著作年月次序而编辑的。这部选集尽可能地搜集了一些为各地方过去印行的集子还没有包括在内的重要著作。选集中的各篇著作，都经著者校阅过，其中有些地方著者曾作了一些文字上的修正，也有个别的文章曾作了一些内容上的补充和修改。",R.mipmap.book4);
        Electronicbook electronicbook4=new Electronicbook("嫌疑人X的献身","百年一遇的数学天才石神，每天唯一的乐趣，便是去固定的便当店买午餐，只为看一眼在便当店做事的邻居靖子。\n" +
                "靖子与女儿相依为命，失手杀了前来纠缠的前夫。石神提出由他料理善后。石神设了一个匪夷所思的局，令警方始终只能在外围敲敲打打，根本无法与案子沾边。石神究竟使用了什么手法？\n",R.mipmap.book5);
        Electronicbook electronicbook5=new Electronicbook("深入React技术栈","全面讲述React技术栈的第一本原创图书，pure render专栏主创倾力打造\n" +
                "覆盖React、Flux、Redux及可视化，帮助开发者在实践中深入理解技术和源码\n" +
                "前端组件化主流解决方案，一本书玩转React“全家桶”\n",R.mipmap.book6);
        Electronicbook electronicbook6=new Electronicbook("Spring实战（第4版）","《Spring实战（第4版）》是经典的、畅销的Spring学习和实践指南。\n" +
                "\n" +
                "《Spring实战（第4版）》适用于已具有一定Java 编程基础的读者，以及在Java 平台下进行各类软件开发的开发人员、测试人员，尤其适用于企业级Java 开发人员。本书既可以被刚开始学习Spring 的读者当作学习指南，也可以被那些想深入了解Spring 某方面功能的专业用户作为参考用书。\n",R.mipmap.book7);
        Electronicbook electronicbook7=new Electronicbook("百年孤独","《百年孤独》是魔幻现实主义文学的代表作，描写了布恩迪亚家族七代人的传奇故事，以及加勒比海沿岸小镇马孔多的百年兴衰，反映了拉丁美洲一个世纪以来风云变幻的历史。作品融入神话传说、民间故事、宗教典故等神秘因素，巧妙地糅合了现实与虚幻，展现出一个瑰丽的想象世界，成为20世纪最重要的经典文学巨著之一。1982年加西亚•马尔克斯获得诺贝尔文学奖，奠定世界级文学大师的地位，很大程度上乃是凭借《百年孤独》的巨大影响",R.mipmap.book9);

        electronicbookList.add(electronicbook);
        electronicbookList.add(electronicbook1);
        electronicbookList.add(electronicbook2);
        electronicbookList.add(electronicbook3);
        electronicbookList.add(electronicbook4);
        electronicbookList.add(electronicbook5);
        electronicbookList.add(electronicbook6);
        electronicbookList.add(electronicbook7);
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
