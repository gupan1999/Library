
package com.example.version1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.version1.Model.MyElectronicBookshelf;
import com.example.version1.R;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;

        import java.util.ArrayList;
        import java.util.List;


public class MyElectronicBookshelves extends AppCompatActivity {
    private RecyclerView myelectronicbookshelfre;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private List<MyElectronicBookshelf> myElectronicBookshelfList=new ArrayList<MyElectronicBookshelf>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_electronic_bookshelf);
        myelectronicbookshelfre=findViewById(R.id.myelectronicbookshelfre);
        MyElectronicBookshelf myElectronicBookshelf=new MyElectronicBookshelf("散文江湖","形散神不散，优美娓娓来。散文，以其优美的文字，丰富的意向，舒缓的情调让人喜爱。或歌颂，或致意，或赞美...",R.mipmap.book2);
        MyElectronicBookshelf myElectronicBookshelf1=new MyElectronicBookshelf("时间移民","选取25岁以下的人类成员向未来移民。旅行队伍进行了多次停留，但每一次的地球环境都不再适合人类居住...",R.mipmap.book1);
        myElectronicBookshelfList.add(myElectronicBookshelf);
        myElectronicBookshelfList.add(myElectronicBookshelf1);

        baseRecyclerAdapter=new BaseRecyclerAdapter<MyElectronicBookshelf>(this,R.layout.myelectronicbookshelfitem,myElectronicBookshelfList){
            @Override
            public void convert(BaseViewHolder holder, MyElectronicBookshelf myElectronicBookshelf) {
                holder.setText(R.id.textView7,myElectronicBookshelf.getBookname());
                holder.setText(R.id.textView8,myElectronicBookshelf.getDetails());
                holder.setImageResource(R.id.imageView4,myElectronicBookshelf.getImageid());
            }

            @Override
            public void setting(BaseViewHolder holder) {

            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myelectronicbookshelfre.setLayoutManager(layoutManager);
        myelectronicbookshelfre.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); //item间的分割线
        myelectronicbookshelfre.setAdapter(baseRecyclerAdapter);
    }
}