package com.example.version1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MythingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_mythings);
        ImageButton Return=(ImageButton)findViewById(R.id.imageButton); //返回按钮
        TextView t=(TextView)findViewById(R.id.textView) ; //本页标题
        t.setText("我的");
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MythingsActivity.this.finish();
            }
    });
        final Button Reserve=(Button)findViewById(R.id.reserve);   //返回按钮
        final Button MyLent=(Button)findViewById(R.id.imageButton9);   //我的借阅按钮
        final Button Message=(Button)findViewById(R.id.imageButton13);
        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MythingsActivity.this,MessageActivity.class);
                startActivity(intent);
            }
        });
        Reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MythingsActivity.this,ReserveActivity.class);
                startActivity(intent);
            }
        });  //返回
        MyLent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MythingsActivity.this,MyLentActivity.class);
                startActivity(intent);
            }
        });  //我的借阅

}
//    private void sendRequsestWithOkHttp(){
//        new  Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder().url("http://10.122.217.113/get_data.json").build();
//                    Response response = client.newCall(request).execute();
//                    String responseData = response.body().string();
//                    Log.d("MainActivity",responseData);
////                    showResponse(responseData);
//                    parseWithGSON(responseData);
////                    parseWithJSONObject(responseData);
////                    parseXMLWithSAX(responseData);
////                parseXMLWithPull(responseData);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//    private void parseWithGSON(String jsonData){
//        Gson gson=new Gson();
//        List<Information> appList=gson.fromJson(jsonData,new TypeToken<List<Information>>(){}.getType());
//
//        }

}
