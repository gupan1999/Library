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
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.version1.Model.Electronicbook;
import com.example.version1.MyApplication;
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
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_result);
        recyclerView=findViewById(R.id.recyclerView3);
        int limit=getIntent().getIntExtra("limit",2);
        if(limit==0) {
            adapter = new BaseRecyclerAdapter<Book>(this, R.layout.searchitem, HttpUtil.bookList) {

                @Override
                public void convert(BaseViewHolder holder, Book book) {
                    holder.setText(R.id.titles, book.getBookName());
                    holder.setText(R.id.authors, "作者:" + book.getAuthor());
                    holder.setText(R.id.publisher, "出版社:" + book.getPublisher());
                    holder.setText(R.id.publishdate, "出版时间:" + book.getPublishDate());
                    holder.setText(R.id.isbn, "标准号:" + book.getIsbn());
                    holder.setText(R.id.price, "价格:" + book.getPrice());
                    holder.setText(R.id.callnumber, "索书号:" + book.getCallNumber());
                    holder.setText(R.id.lend, "可借/总藏:" + book.getLendn() + "/" + book.getColln());
                }

                @Override
                public void setting(final BaseViewHolder holder) {
                    holder.getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {                       //设置长按监听，只为获取选中的ViewHolder的位置
                            final Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
                            final String bookname = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getBookName();
                            String bookno = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getBookno();
                            final int from = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getFrom();
                            handler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    switch (msg.what) {
                                        //当加载网络失败执行的逻辑代码
                                        case HttpUtil.FAIL:
                                            Toast.makeText(MyApplication.getContext(), "网络出现了问题", Toast.LENGTH_SHORT).show();
                                            break;
                                        case HttpUtil.SUCCESS:
                                            intent.putExtra("bookname", bookname);
                                            startActivity(intent);
                                            break;
                                    }
                                }
                            };
                            HttpUtil.showDetails(handler, bookno, from);

                        }
                    });

                }

            };
        }else if(limit==1){
            adapter = new BaseRecyclerAdapter<Book>(this, R.layout.searchallitem, HttpUtil.bookList) {

                @Override
                public void convert(BaseViewHolder holder, Book book) {
                    System.out.println(book.getBookno());
                    Log.d("ResultActivity", book.getBookName());
                    holder.setText(R.id.titles, book.getBookName());
                    holder.setText(R.id.authors, "作者:" + book.getAuthor());
                    holder.setText(R.id.publisher, "出版社:" + book.getPublisher());
                    holder.setText(R.id.publishdate, "出版时间:" + book.getPublishDate());
                    holder.setText(R.id.isbn, "标准号:" + book.getIsbn());
                    holder.setText(R.id.price, "价格:" + book.getPrice());
                    holder.setText(R.id.callnumber, "索书号:" + book.getCallNumber());
                    holder.setText(R.id.lend, "可借/总藏:" + book.getLendn() + "/" + book.getColln());
                    switch (book.getFrom()){
                        case 0:holder.setText(R.id.from,"本校");
                        break;
                        case 1:holder.setText(R.id.from,"他校1");
                        break;
                        default:
                    }
                }

                @Override
                public void setting(final BaseViewHolder holder) {


                        holder.getItemView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final int from = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getFrom();
                                if (from==0) {
                                    final Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
                                    final String bookname = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getBookName();
                                    String bookno = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getBookno();
                                    handler = new Handler() {
                                        @Override
                                        public void handleMessage(Message msg) {
                                            switch (msg.what) {
                                                //当加载网络失败执行的逻辑代码
                                                case HttpUtil.FAIL:
                                                    Toast.makeText(MyApplication.getContext(), "网络出现了问题", Toast.LENGTH_SHORT).show();
                                                    break;
                                                case HttpUtil.SUCCESS:
                                                    intent.putExtra("bookname", bookname);
                                                    startActivity(intent);
                                                    break;
                                            }
                                        }
                                    };
                                    HttpUtil.showDetails(handler, bookno, from);
                                }else{
                                    Toast.makeText(MyApplication.getContext(), "您没有查看他校书籍的权限", Toast.LENGTH_SHORT).show();

                                }
                            }


                        });

                }

            };
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView.setLayoutManager(layoutManager);  //设置为线性布局管理
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//每行划分割线
        System.out.println(HttpUtil.bookList);
        recyclerView.setAdapter(adapter);
        search_nodata=findViewById(R.id.search_nodata);
        total=findViewById(R.id.total);
        total.setText("共 "+HttpUtil.bookList.size()+" 条搜索结果");
        checkNull();
    }

    private void checkNull(){
        if (adapter.getItemCount()==0)search_nodata.setVisibility(View.VISIBLE);
        else search_nodata.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtil.bookList.clear();
    }
}
