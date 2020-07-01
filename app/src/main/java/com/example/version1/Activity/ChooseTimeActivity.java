package com.example.version1.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.version1.R;
import com.example.version1.Util.BaseRecyclerAdapter;
import com.example.version1.Util.BaseViewHolder;
import com.example.version1.Util.HttpUtil;
import com.example.version1.Util.TimeUtil;
import com.example.version1.customed.CustomClickListener;
import com.example.version1.customed.TitleLayout;
import com.example.version1.manager.HttpManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ChooseTimeActivity extends AppCompatActivity {
    private RecyclerView dayView;
    private RecyclerView hourView;
    private RecyclerView minuteView;
    private RecyclerView timeView;
    private BaseRecyclerAdapter<String>dayAdapter;
    private BaseRecyclerAdapter<String>hourAdapter;
    private BaseRecyclerAdapter<String>minuteAdapter;
    private BaseRecyclerAdapter<String>timeAdapter;
    private ArrayList<String> timeList=new ArrayList<>();
    private String dayStr = "";
    private String minuteStr = "";
    private String hourStr = "";
    private String startStr;
    private String endStr;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_time);
        dayView = findViewById(R.id.days);
        hourView = findViewById(R.id.hours);
        minuteView = findViewById(R.id.minutes);
        timeView = findViewById(R.id.recyclerView6);
        TitleLayout titleLayout = findViewById(R.id.titleLayout8);
        final String name = getIntent().getStringExtra("name");
        titleLayout.setTitle(name);
        progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        Button reserve = findViewById(R.id.button2);
        HttpUtil.getReservedDates(name, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (resultJson != null) {
                    JSONObject response = JSON.parseObject(resultJson);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                    JSONArray list = response.getJSONArray("[]");
                    if (list == null) {
                        list = new JSONArray();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject listItem = list.getJSONObject(i);
                        if (listItem == null) {
                            continue;
                        }
                        System.out.println("\nlistItem = list[" + i + "] = \n" + listItem + "\n\n");

                        JSONObject reserve = listItem.getJSONObject("Reserve");

                        String start = reserve.getString("start").replace(".0", "");
                        System.out.println("reserve.start = " + start);
                        String end = reserve.getString("end").replace(".0", "");
                        System.out.println("reserve.end = " + end);
                        try {
                            Date en = sdf.parse(end);
                            Date st = sdf.parse(start);
                            if (en.getTime() >= System.currentTimeMillis())
                                timeList.add(String.format("%s  -  %s", sdf.format(st), sdf.format(en)));
                        } catch (Exception e1) {
                            e.printStackTrace();
                        }


                    }
                    progressBar.setVisibility(View.GONE);
                    timeAdapter.notifyDataSetChanged();
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ChooseTimeActivity.this, "服务器连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }
        });

        timeAdapter = new BaseRecyclerAdapter<String>(this,R.layout.time_ranges,timeList) {
            @Override
            public void convert(BaseViewHolder holder, String s) {
                holder.setText(R.id.textView34,s);
                System.out.println(s);
            }

            @Override
            public void setting(BaseViewHolder holder) {

            }
        };

        dayAdapter = new BaseRecyclerAdapter<String>(this,R.layout.days_item,TimeUtil.getDataList(7)) {
            @Override
            public void convert(BaseViewHolder holder, String s) {
                holder.setText(R.id.day,s);
            }

            @Override
            public void setting(final BaseViewHolder holder) {
                holder.getItemView().setOnClickListener(new CustomClickListener() {

                    @Override
                    protected void onSingleClick() {
                        notifyChosen(R.id.textView26, dayAdapter.getmDataByPosition(holder.getAdapterPosition()),0);

                    }
                    @Override
                    protected void onFastClick() {
                    }
                });
            }
        };
        hourAdapter = new BaseRecyclerAdapter<String>(this,R.layout.hours_item,TimeUtil.getHours()) {
            @Override
            public void convert(BaseViewHolder holder, String s) {
                holder.setText(R.id.day,s);
            }

            @Override
            public void setting(final BaseViewHolder holder) {
                holder.getItemView().setOnClickListener(new CustomClickListener() {

                    @Override
                    protected void onSingleClick() {
                        notifyChosen(R.id.textView31,hourAdapter.getmDataByPosition(holder.getAdapterPosition()),1);
                    }
                    @Override
                    protected void onFastClick() {
                    }
                });
            }
        };
        minuteAdapter = new BaseRecyclerAdapter<String>(this,R.layout.hours_item,TimeUtil.getMinutes()) {
            @Override
            public void convert(BaseViewHolder holder, String s) {
                holder.setText(R.id.day,s);
            }

            @Override
            public void setting(final BaseViewHolder holder) {
                holder.getItemView().setOnClickListener(new CustomClickListener() {

                    @Override
                    protected void onSingleClick() {
                        notifyChosen(R.id.textView33,minuteAdapter.getmDataByPosition(holder.getAdapterPosition()),2);

                    }
                    @Override
                    protected void onFastClick() {
                    }
                });
            }
        };
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局管理Recyclerview
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dayView.setLayoutManager(layoutManager);  //设置为线性布局管理
        LinearLayoutManager layoutManager_2 =new LinearLayoutManager(this);//线性布局管理Recyclerview
        LinearLayoutManager layoutManager_3 =new LinearLayoutManager(this);//线性布局管理Recyclerview
        hourView.setLayoutManager(layoutManager_2);
        minuteView.setLayoutManager(layoutManager_3);
        hourView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//每行划分割线
        minuteView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//每行划分割线
        LinearLayoutManager layoutManager_4 =new LinearLayoutManager(this);//线性布局管理Recyclerview
        timeView.setLayoutManager(layoutManager_4);
        timeView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//每行划分割线

        timeView.setAdapter(timeAdapter);
        dayView.setAdapter(dayAdapter);
        hourView.setAdapter(hourAdapter);
        minuteView.setAdapter(minuteAdapter);
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
                Date start = sdf.parse(String.format("%s %s:%s",dayStr,hourStr,minuteStr));
                startStr = String.format("%s %s:%s",dayStr,hourStr,minuteStr);
                TextView t = findViewById(R.id.textView30);
                int interval = Integer.parseInt(t.getText().toString());
                Calendar c = Calendar.getInstance();
                c.setTime(start);
                endStr = TimeUtil.calFinalTime(c,interval);
                if (!endStr.equals("不合法的预约时段")) {
                    String s = String.format("start:%s end:%s", startStr, endStr);
                    System.out.println(s);
                    if (interval > 0 && interval <= 300) { //限制预约5小时
                        HttpUtil.reserve(name, startStr, endStr, new HttpManager.OnHttpResponseListener() {

                            @Override
                            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                                if (resultJson != null) {
                                    System.out.println(resultJson);
                                    JSONObject response = JSON.parseObject(resultJson);
                                    JSONObject reserve = response.getJSONObject("reserve");
                                    int code = response.getIntValue("code");
                                    String msg = response.getString("msg");
                                    if (code == 200) {
                                        long id = reserve.getLongValue("id");
                                        System.out.println("reserve.id = " + id);
                                        Toast.makeText(ChooseTimeActivity.this, "预约成功,id:" + String.valueOf(id), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ChooseTimeActivity.this, "预约失败," + msg, Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(ChooseTimeActivity.this, "服务器连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(ChooseTimeActivity.this, "不合法的预约时段", Toast.LENGTH_SHORT).show();
                }
                }catch (NumberFormatException e){
                    Toast.makeText(ChooseTimeActivity.this, "请输入正确的持续时间", Toast.LENGTH_SHORT).show();
                    }
                catch (ParseException e) {
                    Toast.makeText(ChooseTimeActivity.this, "请确认时间信息是否完整", Toast.LENGTH_SHORT).show();
                }
                }
            });
        }
    private void notifyChosen(int id,String str,int type){
        switch (type){
            case 0:dayStr=str;
            break;
            case 1:hourStr=str;
            break;
            case 2:minuteStr=str;
            break;

        }
        TextView textView = findViewById(id);
        textView.setText(str);
    }
}