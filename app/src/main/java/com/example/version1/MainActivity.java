package com.example.version1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {


   static List<Information>appList;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_search:

                    Intent intent_search=new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent_search);

                    return true;


                case R.id.navigation_hot:

                    Intent intent_hot=new Intent(MainActivity.this, HotActivity.class);
                    startActivity(intent_hot);
                    return true;
                case R.id.navigation_categories:

                    Intent intent_categories=new Intent(MainActivity.this, CategoriesActivity.class);
                    startActivity(intent_categories);
                    return true;
                case R.id.navigation_mythings:
                    sendRequsestWithOkHttp();
                    Intent intent_mythings=new Intent(MainActivity.this, MythingsActivity.class);


                    startActivity(intent_mythings);
                    return true;

            }
          return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.button);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void sendRequsestWithOkHttp(){
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.122.217.113/get_userdata.json").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("Information",responseData);
//                    showResponse(responseData);
                    parseWithGSON(responseData);
//                    parseWithJSONObject(responseData);
//                    parseXMLWithSAX(responseData);
//                parseXMLWithPull(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parseWithGSON(String jsonData){
        Gson gson=new Gson();
         appList=gson.fromJson(jsonData,new TypeToken<List<Information>>(){}.getType());
//        for (Information information:appList){
//            Log.d("Information",information.bookName);
//            Log.d("Information",information.lentTime);
//        }

    }

}
