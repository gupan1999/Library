package com.example.version1.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.version1.greendao.Information;
import com.example.version1.greendao.User;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.greendao.LentInformation;
import com.example.version1.greendao.MessageInformation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    public static List<Information>informationList;


    public static void getInformation(){
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();    //默认参数的OkHttpClient，可连缀设置各种参数
                    Request request = new Request.Builder().url("http://10.128.201.6/get_userdata.json").build();  //连缀设置url地址的Request对象
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();   //返回字符串
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
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private static void parseWithGSON(String jsonData){
        Gson gson=new Gson();
        informationList=gson.fromJson(jsonData,new TypeToken<List<Information>>(){}.getType());
    }

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


}
