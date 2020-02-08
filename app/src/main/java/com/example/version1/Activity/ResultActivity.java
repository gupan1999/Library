package com.example.version1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.version1.Model.Book;
import com.example.version1.Model.Collin;
import com.example.version1.Model.Decorater;
import com.example.version1.Model.Results;
import com.example.version1.MyApplication;
import com.example.version1.R;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.Util.HttpManager;
import com.example.version1.Util.HttpUtil;
import com.example.version1.customed.CustomClickListener;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter adapter;
    private TextView search_nodata;
    private TextView total;
    private Handler handler;
    private Button previous;
    private Button next;
    private TextView page;
    private int curpageno;
    private int totalpage;
    private String pginfo;
    private String key;
    private int type;
    private int limit;
    private int cnt = 0;
    private HttpManager.OnHttpResponseListener nextPageListener;
    private HttpManager.OnHttpResponseListener previousPageListener;
    private HttpManager.OnHttpResponseListener detailListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_result);
        recyclerView = findViewById(R.id.recyclerView3);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        page = findViewById(R.id.page);
        curpageno = 1;
        limit = getIntent().getIntExtra("limit", -1);
        key = getIntent().getStringExtra("key");
        type = getIntent().getIntExtra("type", -1);
        initCnt();
        totalpage = HttpUtil.page(cnt, HttpUtil.pgcnt);
        next.setOnClickListener(new CustomClickListener(500L) {
            @Override
            protected void onSingleClick() {
                if (curpageno < totalpage) {
                    curpageno++;
                    pginfo = curpageno + "/" + totalpage;
                    HttpUtil.bookList.clear();
                    nextPageListener = new HttpManager.OnHttpResponseListener() {
                        int cnt = -1;

                        @Override
                        public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                            if (limit == HttpUtil.ALL_SCHOOL)
                                resultJson = resultJson.replace(HttpUtil.libraries[requestCode], HttpUtil.libraries[0]);
                            Results results = JSON.parseObject(resultJson, Results.class);
                            List<Decorater> decoraterList = results.getDecoraterList();
                            if (decoraterList != null) {
                                for (Decorater decorater : decoraterList) {
                                    Book book = decorater.getBook();
                                    book.setFrom(requestCode);
                                    HttpUtil.bookList.add(book);
                                }
                            }
                            cnt++;
                            if (cnt == limit) {
                                adapter.notifyDataSetChanged();
                                page.setText(pginfo);
                            }
                        }
                    };
                    if (limit == HttpUtil.MY_SCHOOL)
                        HttpUtil.getBook(0, key, type, HttpUtil.pgcnt, curpageno - 1, nextPageListener);
                    else {
                        HttpUtil.getBook(0, key, type, HttpUtil.pgcnt / 2, curpageno - 1, nextPageListener);
                        HttpUtil.getBook(1, key, type, HttpUtil.pgcnt / 2, curpageno - 1, nextPageListener);
                    }
                }
            }

            @Override
            protected void onFastClick() {

            }
        });
        previous.setOnClickListener(new CustomClickListener(500L) {
            @Override
            protected void onSingleClick() {
                if (curpageno > 1) {
                    curpageno--;
                    pginfo = curpageno + "/" + totalpage;
                    HttpUtil.bookList.clear();
                    previousPageListener = new HttpManager.OnHttpResponseListener() {
                        int cnt = -1;

                        @Override
                        public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                            if (limit == HttpUtil.ALL_SCHOOL)
                                resultJson = resultJson.replace(HttpUtil.libraries[requestCode], HttpUtil.libraries[0]);
                            Results results = JSON.parseObject(resultJson, Results.class);
                            List<Decorater> decoraterList = results.getDecoraterList();
                            if (decoraterList != null) {
                                for (Decorater decorater : decoraterList) {
                                    Book book = decorater.getBook();
                                    book.setFrom(requestCode);
                                    HttpUtil.bookList.add(book);

                                }
                            }
                            cnt++;
                            if (cnt == limit) {
                                adapter.notifyDataSetChanged();
                                page.setText(pginfo);
                            }
                        }
                    };
                    if (limit == HttpUtil.MY_SCHOOL)
                        HttpUtil.getBook(0, key, type, HttpUtil.pgcnt, curpageno - 1, previousPageListener);
                    else {
                        HttpUtil.getBook(0, key, type, HttpUtil.pgcnt / 2, curpageno - 1, previousPageListener);
                        HttpUtil.getBook(1, key, type, HttpUtil.pgcnt / 2, curpageno - 1, previousPageListener);
                    }
                }
            }

            @Override
            protected void onFastClick() {

            }
        });

        if (limit == 0) {
            pginfo = curpageno + "/" + totalpage;
            page.setText(pginfo);
            adapter = new BaseRecyclerAdapter<Book>(this, R.layout.searchitem, HttpUtil.bookList) {

                @Override
                public void convert(BaseViewHolder holder, Book book) {
                    int no = holder.getAdapterPosition() + 1 + (curpageno - 1) * HttpUtil.pgcnt;
                    holder.setText(R.id.titles, no + "." + book.getBookName());
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
                    holder.getItemView().setOnClickListener(new CustomClickListener() {
                        @Override
                        protected void onSingleClick() {
                            final Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
                            final String bookname = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getBookName();
                            String bookno = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getBookno();
                            final int from = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getFrom();
                            detailListener=new HttpManager.OnHttpResponseListener() {
                                @Override
                                public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                                    if (limit == HttpUtil.ALL_SCHOOL)
                                        resultJson = resultJson.replace(HttpUtil.details[requestCode], HttpUtil.details[0]);
                                    Results results = JSON.parseObject(resultJson, Results.class);
                                    List<Decorater> decoraterList = results.getDecoraterList();
                                    if (decoraterList != null) {
                                        for (Decorater decorater : decoraterList) {
                                            Collin collin = decorater.getCollin();
                                            HttpUtil.collinList.add(collin);
                                        }
                                    }
                                    intent.putExtra("bookname", bookname);
                                    startActivity(intent);
                                }
                            };
                            HttpUtil.getColl(from,bookno,detailListener);

                        }

                        @Override
                        protected void onFastClick() {

                        }
                    });
                }
            };

        } else if (limit == 1) {
            pginfo = curpageno + "/" + totalpage;
            page.setText(pginfo);
            adapter = new BaseRecyclerAdapter<Book>(this, R.layout.searchallitem, HttpUtil.bookList) {

                @Override
                public void convert(BaseViewHolder holder, Book book) {
                    int no = holder.getAdapterPosition() + 1 + (curpageno - 1) * HttpUtil.pgcnt;
                    holder.setText(R.id.titles, no + "." + book.getBookName());
                    holder.setText(R.id.authors, "作者:" + book.getAuthor());
                    holder.setText(R.id.publisher, "出版社:" + book.getPublisher());
                    holder.setText(R.id.publishdate, "出版时间:" + book.getPublishDate());
                    holder.setText(R.id.isbn, "标准号:" + book.getIsbn());
                    holder.setText(R.id.price, "价格:" + book.getPrice());
                    holder.setText(R.id.callnumber, "索书号:" + book.getCallNumber());
                    holder.setText(R.id.lend, "可借/总藏:" + book.getLendn() + "/" + book.getColln());
                    switch (book.getFrom()) {
                        case 0:
                            holder.setText(R.id.from, "本校");
                            break;
                        case 1:
                            holder.setText(R.id.from, "他校1");
                            break;
                        default:
                    }
                }

                @Override
                public void setting(final BaseViewHolder holder) {
                    holder.getItemView().setOnClickListener(new CustomClickListener() {
                        @Override
                        protected void onSingleClick() {
                            final int from = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getFrom();
                            if (from == 0) {
                                final Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
                                final String bookname = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getBookName();
                                String bookno = ((Book) adapter.getmDataByPosition(holder.getAdapterPosition())).getBookno();
                                detailListener=new HttpManager.OnHttpResponseListener() {
                                    @Override
                                    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                                        resultJson = resultJson.replace(HttpUtil.details[requestCode], HttpUtil.details[0]);
                                        Results results = JSON.parseObject(resultJson, Results.class);
                                        List<Decorater> decoraterList = results.getDecoraterList();
                                        if (decoraterList != null) {
                                            for (Decorater decorater : decoraterList) {
                                                Collin collin = decorater.getCollin();
                                                HttpUtil.collinList.add(collin);
                                            }
                                        }
                                        intent.putExtra("bookname", bookname);
                                        startActivity(intent);
                                    }
                                };
                                HttpUtil.getColl(from,bookno,detailListener);
                            } else {
                                Toast.makeText(MyApplication.getContext(), "您没有查看他校书籍的权限", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        protected void onFastClick() {

                        }
                    });
                }
            };
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView.setLayoutManager(layoutManager);  //设置为线性布局管理
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//每行划分割线
        System.out.println(HttpUtil.bookList);
        recyclerView.setAdapter(adapter);
        search_nodata = findViewById(R.id.search_nodata);
        total = findViewById(R.id.total);
        total.setText("共 " + cnt + " 条搜索结果");
        checkNull();
    }

    private void initCnt() {
        if (limit == 0) {
            cnt = getIntent().getIntExtra("total0", -1);
        } else if (limit == 1) {
            for (int i = 0; i < HttpUtil.libraries.length; i++) {
                cnt += getIntent().getIntExtra("total" + i, -1);

            }
        }
    }

    private void checkNull() {
        if (adapter.getItemCount() == 0) search_nodata.setVisibility(View.VISIBLE);
        else search_nodata.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtil.bookList.clear();
        //for(int i=0;i<HttpUtil.pages.length;i++)HttpUtil.pages[i]=0;
    }
}
