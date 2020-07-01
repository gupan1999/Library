package com.example.version1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.version1.Model.Book;
import com.example.version1.Model.Collin;
import com.example.version1.R;
import com.example.version1.Util.BaseActivity;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.Util.HttpUtil;
import com.example.version1.customed.CustomClickListener;
import com.example.version1.manager.HttpManager;


public class ResultActivity extends BaseActivity {
    public static final String TAG="ResultActivity";
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter<Book> adapter;
    private TextView search_nodata;
    private TextView page;
    private int curpageno;
    private int totalpage;
    private String pginfo;
    private String key;
    private int type;
    private int limit;
    private HttpManager.OnHttpResponseListener nextPageListener;
    private HttpManager.OnHttpResponseListener previousPageListener;
    private HttpManager.OnHttpResponseListener detailListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_result);
        recyclerView = findViewById(R.id.recyclerView3);
        Button previous = findViewById(R.id.previous);
        Button next = findViewById(R.id.next);
        page = findViewById(R.id.page);
        curpageno = 1;
        limit = getIntent().getIntExtra("limit", -1);
        key = getIntent().getStringExtra("key");
        type = getIntent().getIntExtra("type", -1);
        //initCnt();
        int cnt=getIntent().getIntExtra("total",-1);
        totalpage = HttpUtil.page(cnt, HttpUtil.pgcnt);
        if (cnt==0){
            previous.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            page.setVisibility(View.GONE);
        }
        next.setOnClickListener(new CustomClickListener(500L) {
            @Override
            protected void onSingleClick() {
                if (curpageno < totalpage) {
                    curpageno++;
                    pginfo = curpageno + "/" + totalpage;
                    HttpUtil.bookList.clear();
                    nextPageListener = new HttpManager.OnHttpResponseListener() {

                        @Override
                        public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                            if (limit == HttpUtil.OTHER_SCHOOL1)
                                resultJson = resultJson.replace(HttpUtil.libraries[requestCode], HttpUtil.libraries[0]);
                            JSONObject response = JSON.parseObject(resultJson);
                            JSONArray List = response.getJSONArray("[]");
                            if (List == null) {
                                List = new JSONArray();
                            }
                            JSONObject item;
                            for (int i = 0; i < List.size(); i ++) {
                                item = List.getJSONObject(i);
                                if (item == null) {
                                    continue;
                                }
                                Book book = item.getObject("Book", Book.class);
                                book.setFrom(requestCode);
                                HttpUtil.bookList.add(book);
                            }
                                adapter.notifyDataSetChanged();
                                page.setText(pginfo);

                        }
                    };
                    if (limit == HttpUtil.MY_SCHOOL)
                        HttpUtil.getBook(0, key, type, HttpUtil.pgcnt, curpageno - 1, nextPageListener);
                    else {
                        HttpUtil.getBook(1, key, type, HttpUtil.pgcnt, curpageno - 1, nextPageListener);
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

                        @Override
                        public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                            if (resultJson != null) {
                                if (limit == HttpUtil.OTHER_SCHOOL1)
                                    resultJson = resultJson.replace(HttpUtil.libraries[requestCode], HttpUtil.libraries[0]);
                                JSONObject response = JSON.parseObject(resultJson);
                                JSONArray List = response.getJSONArray("[]");
                                if (List == null) {
                                    List = new JSONArray();
                                }
                                JSONObject item;
                                for (int i = 0; i < List.size(); i ++) {
                                    item = List.getJSONObject(i);
                                    if (item == null) {
                                        continue;
                                    }
                                    Book book = item.getObject("Book", Book.class);
                                    book.setFrom(requestCode);
                                    HttpUtil.bookList.add(book);
                                }
                                adapter.notifyDataSetChanged();
                                page.setText(pginfo);
                            }else  Toast.makeText(ResultActivity.this, "访问服务器失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    };
                    if (limit == HttpUtil.MY_SCHOOL)
                        HttpUtil.getBook(0, key, type, HttpUtil.pgcnt, curpageno - 1, previousPageListener);
                    else {
                        HttpUtil.getBook(1, key, type, HttpUtil.pgcnt , curpageno - 1, previousPageListener);
                    }
                }
            }

            @Override
            protected void onFastClick() {

            }
        });


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
                                    if (resultJson != null) {
                                        if (limit == HttpUtil.OTHER_SCHOOL1)
                                            resultJson = resultJson.replace(HttpUtil.details[requestCode], HttpUtil.details[0]);
                                        JSONObject response = JSON.parseObject(resultJson);
                                            JSONArray List = response.getJSONArray("[]");

                                            if (List == null) {
                                                List = new JSONArray();
                                            }

                                            JSONObject item;
                                            for (int i = 0; i < List.size(); i ++) {
                                                item = List.getJSONObject(i);
                                                if (item == null) {
                                                    continue;
                                                }
                                                Collin collin = item.getObject("Collin", Collin.class);
                                                if (collin == null) {
                                                    collin = new Collin();
                                                }
                                                HttpUtil.collinList.add(collin);

                                            }

                                        intent.putExtra("bookname", bookname);
                                        startActivity(intent);
                                    }else Toast.makeText(ResultActivity.this, "访问服务器失败，请稍后重试", Toast.LENGTH_SHORT).show();


                                }
                            };
                            if(from== HttpUtil.MY_SCHOOL) HttpUtil.getColl(from,bookno,detailListener);
                            else   Toast.makeText(ResultActivity.this, "您没有查看此库书籍的权限", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        protected void onFastClick() {

                        }
                    });
                }
            };


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView.setLayoutManager(layoutManager);  //设置为线性布局管理
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//每行划分割线
        System.out.println(HttpUtil.bookList);
        recyclerView.setAdapter(adapter);
        search_nodata = findViewById(R.id.search_nodata);
        TextView total = findViewById(R.id.total);
        total.setText("共 " + cnt + " 条搜索结果");
        checkNull();
    }


    private void checkNull() {
        if (adapter.getItemCount() == 0) search_nodata.setVisibility(View.VISIBLE);
        else search_nodata.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtil.bookList.clear();
    }
}
