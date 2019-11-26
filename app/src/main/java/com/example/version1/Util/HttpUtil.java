package com.example.version1.Util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.version1.Model.Book;
import com.example.version1.Model.DaoSession;
import com.example.version1.Model.Decorater;
import com.example.version1.Model.GreenDaoManager;
import com.example.version1.Model.Information;
import com.example.version1.Model.LentInformation;
import com.example.version1.Model.MessageInformation;
import com.example.version1.Model.Results;
import com.example.version1.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    public static List<Information>informationList;
    public static List<Book>bookList;
    public static final int SUCCESS = 1;
    public static final int FAIL = 2;

    public static String responseData;//返回字符串
    //public static String host="http://10.128.211.178";
    //public static  String host="http://167.179.66.196";
    public static String host="http://10.28.187.251";
    public static String requestjson;
    public static String userdata="/get_userdata.json";
    public static String picture="";
    public static void getInformation(final Handler handler){
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS) //连接超时
                            .readTimeout(5,TimeUnit.SECONDS) //读取超时
                            .writeTimeout(5,TimeUnit.SECONDS).build(); //写超时;    //默认参数的OkHttpClient，可连缀设置各种参数
                    Request request = new Request.Builder().url("http://10.128.243.29"+userdata).build();  //连缀设置url地址的Request对象
                    //Response response = client.newCall(request).execute();
                    Call call=client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message message=handler.obtainMessage();
                            message.what=FAIL;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //得到从网上获取资源，转换成我们想要的类型
                            responseData = response.body().string();
                            //通过handler更新UI
                            Message message = handler.obtainMessage();
                            //message.obj=responseData;
                            message.what = SUCCESS;
                            parseWithGSON(responseData);            //通过Gson解析
                            User.mesList = Temp.getMessageList(informationList); //将解析得到的Information List转为需要的两种List
                            User.leList = Temp.getLentList(informationList);
                            DaoSession daoSession = GreenDaoManager.getInstance().getDaoSession();
                            daoSession.getMessageInformationDao().deleteAll();      //数据库清空旧数据
                            daoSession.getLentInformationDao().deleteAll();
                            for(MessageInformation msg:User.mesList) {
                                daoSession.getMessageInformationDao().insertOrReplace(msg); //数据库添加新数据
                            }
                            for(LentInformation lei:User.leList){
                                daoSession.getLentInformationDao().insertOrReplace(lei);
                            }
                            handler.sendMessage(message);
                        }
                    });



                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private static void  setRequestjson(final String key){
        Log.d("HttpUtil",key);
        requestjson="/get/{ '[]': { 'Book': { 'bookname$': '%25"+key+"%25' } } }";
    }
    public static void queryTitle(final Handler handler,final String key){
        setRequestjson(key);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS) //连接超时
                            .readTimeout(5, TimeUnit.SECONDS) //读取超时
                            .writeTimeout(5, TimeUnit.SECONDS).build(); //写超时;    //默认参数的OkHttpClient，可连缀设置各种参数
                    Request request = new Request.Builder().url("http://10.128.243.29:8888" + requestjson).build();  //连缀设置url地址的Request对象
                    Call call=client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message message=handler.obtainMessage();
                            message.what=FAIL;
                            Log.d("HttpUtil","Failed");
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //得到从网上获取资源，转换成我们想要的类型
                            responseData = response.body().string();
                            //通过handler更新UI
                            Message message = handler.obtainMessage();
                            //message.obj=responseData;
                            Log.d("HttpUtil",responseData);
                            message.what=paraseWithFastjson(handler,responseData);
                            handler.sendMessage(message);
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
    private static int paraseWithFastjson(Handler handler,String jsonData) {
        List<Book> templist = new ArrayList<Book>();
        Results results = JSON.parseObject(jsonData, Results.class);
        List<Decorater> decoraterList = results.getDecoraterList();
        if (decoraterList != null) {
            for (Decorater decorater : decoraterList) {
                Book book = decorater.getBook();
                templist.add(book);
            }
        }
        bookList = templist;
        return SUCCESS;
    }
    private static void parseWithGSON(String jsonData){
        Gson gson=new Gson();
        informationList=gson.fromJson(jsonData,new TypeToken<List<Information>>(){}.getType());
    }

    /*
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            System.out.println(mNetworkInfo);

            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();

            }
        }
        return false;
    }
    */

}