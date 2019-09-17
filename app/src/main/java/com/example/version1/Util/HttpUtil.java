package com.example.version1.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.example.version1.greendao.Information;
import com.example.version1.greendao.User;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.greendao.LentInformation;
import com.example.version1.greendao.MessageInformation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    public static List<Information>informationList;
    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    public static String responseData;//返回字符串
    public static void getInformation(final Handler handler){
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS) //连接超时
                            .readTimeout(5,TimeUnit.SECONDS) //读取超时
                            .writeTimeout(5,TimeUnit.SECONDS).build(); //写超时;    //默认参数的OkHttpClient，可连缀设置各种参数
                    Request request = new Request.Builder().url("http://10.128.221.86/get_userdata.json").build();  //连缀设置url地址的Request对象
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
