package com.example.version1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.version1.R;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.Util.HttpUtil;
import com.example.version1.Model.Book;

public class ResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter adapter;
    private TextView search_nodata;
    private TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        recyclerView=findViewById(R.id.recyclerView3);

        adapter = new BaseRecyclerAdapter<Book>(this, R.layout.searchitem, HttpUtil.bookList) {
            @Override
            public void convert(BaseViewHolder holder,Book book) {
                Log.d("ResultActivity",book.getBookName());
                holder.setText(R.id.titles, book.getBookName());
                holder.setText(R.id.authors, "作者:"+book.getAuthor());
                holder.setText(R.id.publisher,"出版社:"+book.getPublisher());
                holder.setText(R.id.publishdate,"出版时间:"+book.getPublishDate());
                holder.setText(R.id.isbn,"标准号:"+book.getIsbn());
                holder.setText(R.id.price,"价格:"+book.getPrice());
                holder.setText(R.id.callnumber,"索书号:"+book.getCallNumber());
                holder.setText(R.id.lend,"可借/总藏:"+book.getLendn()+"/"+book.getColln());
            }

            @Override
            public void setting(BaseViewHolder holder) {

            }

        };
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView.setLayoutManager(layoutManager);  //设置为线性布局管理
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//每行划分割线
        System.out.println(HttpUtil.bookList);
        recyclerView.setAdapter(adapter);
        search_nodata=findViewById(R.id.search_nodata);
        total=findViewById(R.id.total);
        total.setText("共 "+HttpUtil.bookList.size()+" 条搜索结果");
        //   lent_nodata.setVisibility(View.VISIBLE);
        checkNull();
        //      }
    }

    private void checkNull(){
        if (adapter.getItemCount()==0)search_nodata.setVisibility(View.VISIBLE);
        else search_nodata.setVisibility(View.GONE);
    }
}
