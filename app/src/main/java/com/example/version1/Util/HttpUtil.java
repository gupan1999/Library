package com.example.version1.Util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.version1.Model.Book;
import com.example.version1.Model.Collin;
import com.example.version1.greendao.DaoSession;
import com.example.version1.Model.Decorater;
import com.example.version1.greendao.GreenDaoManager;
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

import javax.xml.transform.Result;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    public static List<Information>informationList;
    public static List<Book>bookList=new ArrayList<Book>();
    public static List<Collin>collinList=new ArrayList<Collin>();
    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    public static final int MY_SCHOOL = 0;
    public static final int ALL_SCHOOL = 1;
    public static final int OTHER_SCHOOL1 = 1;
    private static String[] searchKey={"bookname","author","publishdate","isbn"};
    private static String[] libraries={"Book","Bookother"};
    private static String[] details={"Collin","Collinother"};
    public static String responseData;
    //public static String host="http://10.128.245.151";
   public static String host="http://192.168.43.44";
    public static List<String>requestjsons=new ArrayList<String>();
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
                    Request request = new Request.Builder().url(host+userdata).build();  //连缀设置url地址的Request对象
                    //Response response = client.newCall(request).execute();  //同步请求
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
    private static void  setSearchjson(final String key,final int limit,final int type){

        switch (limit) {
            case 0: requestjsons.add(  "/get/{ '[]': { 'Book': { '" + searchKey[type] + "$': '%25" + key + "%25' } } }");

            break;
            case 1: for(int i=0;i<libraries.length;i++){
                requestjsons.add("/get/{ '[]': { '"+libraries[i]+"': { '" + searchKey[type] + "$': '%25" + key + "%25' } } }");
            }
            break;

            default:
            }
        System.out.println(requestjsons);
        }

    private static void setDetailsjson( String bookno,int from){
        switch (from) {
            case MY_SCHOOL:   requestjsons.add( "/get/{ '[]' :  { 'Collin' :  { 'bookno' :'" + bookno + "' } } }");
            break;
            case OTHER_SCHOOL1:
                requestjsons.add("/get/{ '[]' :  { '"+details[OTHER_SCHOOL1]+"' :  { 'bookno' :'" + bookno + "' } } }");


            break;
            default:
        }
        Log.d("HttpUtiljson",requestjsons.get(0));
    }
    public static void query(final Handler handler,final String key,int limit,int type){
        setSearchjson(key,limit,type);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    for (int i=0;i<requestjsons.size();i++) {
                        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS) //连接超时
                            .readTimeout(5, TimeUnit.SECONDS) //读取超时
                            .writeTimeout(5, TimeUnit.SECONDS).addInterceptor(new MyInterceptor(String.valueOf(i))).build();
                        Request request = new Request.Builder().url("http://192.168.43.44:9999" + requestjsons.get(i)).build();  //连缀设置url地址的Request对象
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Message message = handler.obtainMessage();
                                requestjsons.clear();
                                message.what = FAIL;
                                Log.d("HttpUtil", "Failed");
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                //得到从网上获取资源，转换成我们想要的类型
                                Message message = handler.obtainMessage();
                                Request request1=response.request();
                                requestjsons.clear();
                                //通过handler更新UI
                                responseData = response.body().string();
                                message.what=paraseSearchResult(handler, responseData,Integer.parseInt(request1.header("type")));
                                handler.sendMessage(message);
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void showDetails(final Handler handler,final String bookno,int from){
        setDetailsjson(bookno,from);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS) //连接超时
                            .readTimeout(5, TimeUnit.SECONDS) //读取超时
                            .writeTimeout(5, TimeUnit.SECONDS).build(); //写超时;    //默认参数的OkHttpClient，可连缀设置各种参数

                        Request request = new Request.Builder().url("http://192.168.43.44:9999" + requestjsons.get(0)).build();  //连缀设置url地址的Request对象
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Message message = handler.obtainMessage();
                                requestjsons.clear();
                                message.what = FAIL;
                                Log.d("HttpUtil", "Failed");
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                //得到从网上获取资源，转换成我们想要的类型
                                responseData = response.body().string();
                                //通过handler更新UI
                                requestjsons.clear();
                                Message message = handler.obtainMessage();
                                //message.obj=responseData;
                                Log.d("HttpUtil", responseData);
                                message.what = paraseDetailsResult(handler, responseData);
                                handler.sendMessage(message);
                            }
                        });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private static int paraseDetailsResult(Handler handler,String jsonData){
        Log.d("HttpUtildetail",jsonData);
        for(int i=1;i<details.length;i++){
            if(jsonData.contains(details[i])){
                jsonData=jsonData.replaceAll(details[i],"Collin");
            }
        }
        Results results=JSON.parseObject(jsonData,Results.class);
        List<Decorater> decoraterList = results.getDecoraterList();
        if (decoraterList != null) {
            for (Decorater decorater : decoraterList) {
                Collin collin=decorater.getCollin();
                collinList.add(collin);
            }
        }
        return SUCCESS;
    }

    private static int paraseSearchResult(Handler handler,String jsonData,int from) {
        for(int i=1;i<libraries.length;i++){
            if(jsonData.contains(libraries[i])){
               jsonData=jsonData.replaceAll(libraries[i],"Book");
            }
        }
        Results results = JSON.parseObject(jsonData, Results.class);
        List<Decorater> decoraterList = results.getDecoraterList();
        Log.d("HttpUtil",String.valueOf(from));
        if (decoraterList != null) {
            for (Decorater decorater : decoraterList) {
                Book book = decorater.getBook();
                book.setFrom(from);
                bookList.add(book);
            }
        }
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
