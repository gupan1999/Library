package com.example.version1.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.version1.Model.Book;
import com.example.version1.Model.Collin;
import com.example.version1.Model.Lend;
import com.example.version1.R;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.Util.HttpUtil;
import com.example.version1.Model.LentInformation;
import com.example.version1.Model.User;
import com.example.version1.manager.HttpManager;

public class MyLentActivity extends AppCompatActivity {
    public static final String TAG="MyLentActivity";
    private Handler handler;
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private TextView lent_nodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_my_lent);

        recyclerView=findViewById(R.id.recyclerview);

        adapter = new BaseRecyclerAdapter<LentInformation>(this, R.layout.items, HttpUtil.LentinformationinList) {

            @Override
            public void convert(BaseViewHolder holder, LentInformation lentInformation) {
                    holder.setText(R.id.bookName, lentInformation.getBookName());
                    holder.setText(R.id.lentTime, lentInformation.getLentTime());
                    holder.setText(R.id.location,lentInformation.getLocation());
            }

            @Override
            public void setting(BaseViewHolder holder) {

            }
        };
        HttpUtil.getLend(new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (resultJson!=null) {
                    JSONObject response = JSON.parseObject(resultJson);
                    JSONArray List = response.getJSONArray("[]");
                    if (List == null) {
                        List = new JSONArray();
                    }
                    JSONObject item;
                    for (int i = 0; i < List.size(); i++) {
                        item = List.getJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        System.out.println("\nitem = List[" + i + "] = \n" + item + "\n\n");
                        Lend Lend = item.getObject("Lend", Lend.class);
                        if (Lend == null) {
                            Lend = new Lend();
                        }
                        String Registno = Lend.getRegistno();
                        System.out.println("Lend.Registno = " + Registno);
                        String Date = Lend.getDate().replace(".0", "");
                        System.out.println("Lend.Date = " + Date);
                        Collin Collin = item.getObject("Collin", Collin.class);
                        if (Collin == null) {
                            Collin = new Collin();
                        }
                        String Bookno = Collin.getBookno();
                        String Dept = Collin.getDept();
                        String Shelfn = Collin.getShelfn();
                        Book book = item.getObject("Book", Book.class);
                        if (book == null) {
                            book = new Book();
                        }
                        String Bookname = book.getBookName();
                        System.out.println("Book.Bookname = " + Bookname);
                        LentInformation lentInformation = new LentInformation(Bookname, Date, Dept + " " + Shelfn);
                        HttpUtil.LentinformationinList.add(lentInformation);
                        adapter.updateItems(HttpUtil.LentinformationinList);
                        checkNull();
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(MyLentActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
            LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
            recyclerView.setLayoutManager(layoutManager);  //设置为线性布局管理
            recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//每行划分割线
            recyclerView.setAdapter(adapter);
            lent_nodata=findViewById(R.id.lent_nodata);
            checkNull();

        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.
                OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLentInformation();
            }
        });
    }
    private void refreshLentInformation() {
    HttpUtil.getLend(new HttpManager.OnHttpResponseListener() {
        @Override
        public void onHttpResponse(int requestCode, String resultJson, Exception e) {

            adapter.updateItems(HttpUtil.LentinformationinList);
            checkNull();
            adapter.notifyDataSetChanged();
            swipeRefresh.setRefreshing(false);
        }
    });
    }

    @Override
    protected void onDestroy() {
        HttpUtil.LentinformationinList.clear();
        super.onDestroy();
    }

    private void checkNull() {
        if (adapter.getItemCount() == 0) lent_nodata.setVisibility(View.VISIBLE);
        else lent_nodata.setVisibility(View.GONE);
    }
    }



