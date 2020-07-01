package com.example.version1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.example.version1.Model.Lend;
import com.example.version1.Model.LentInformation;
import com.example.version1.R;
import com.example.version1.Util.BaseActivity;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.Util.HttpUtil;
import com.example.version1.Util.TimeUtil;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.manager.DataManager;
import com.example.version1.manager.HttpManager;


public class MyLentActivity extends BaseActivity {
    public static final String TAG="MyLentActivity";
    private BaseRecyclerAdapter adapter;
    private Button off;
    private TextView lent_nodata;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_my_lent);

        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        off = findViewById(R.id.button3);
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.version1.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
        adapter = new BaseRecyclerAdapter<LentInformation>(this, R.layout.items, HttpUtil.LentinformationinList) {


            @Override
            public void convert(BaseViewHolder holder, LentInformation lentInformation) {
                holder.setText(R.id.bookName, lentInformation.getBookName());
                holder.setText(R.id.lentTime, lentInformation.getLentTime());
                holder.setText(R.id.location, lentInformation.getLocation());
            }

            @Override
            public void setting(BaseViewHolder holder) {

            }
        };
        if (DataManager.getInstance().getLastCacheDate() == null || TimeUtil.calDateDifferent(DataManager.getInstance().getLastCacheDate(), TimeUtil.getCurTimeStr()) > 30) {
            HttpUtil.getLend(new HttpManager.OnHttpResponseListener() {
                @Override
                public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                    if (resultJson != null) {
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
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            checkNull();

                            DataManager.getInstance().setLastCacheDate(TimeUtil.getCurTimeStr());

                            DaoSession daoSession = GreenDaoManager.getInstance().getDaoSession();
                            daoSession.getLentInformationDao().deleteAll();      //数据库清空旧数据
                            for (LentInformation l : HttpUtil.LentinformationinList) {
                                daoSession.getLentInformationDao().insertOrReplace(l); //数据库添加新数据
                            }
                        }
                    } else {
                        Toast.makeText(MyLentActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            DaoSession daoSession = GreenDaoManager.getInstance().getDaoSession();
            HttpUtil.LentinformationinList = daoSession.getLentInformationDao().loadAll();
            adapter.updateItems(HttpUtil.LentinformationinList);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            Log.d(TAG, "Load from db");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//线性布局管理Recyclerview
        recyclerView.setLayoutManager(layoutManager);  //设置为线性布局管理
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//每行划分割线
        recyclerView.setAdapter(adapter);
        lent_nodata = findViewById(R.id.lent_nodata);

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



