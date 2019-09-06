package com.example.version1.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.version1.Information;
import com.example.version1.User;
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
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.128.201.6/get_userdata.json").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("Information",responseData);
                     parseWithGSON(responseData);
                    User.mesList = Temp.getMessageList(HttpUtil.informationList);
                    /*
                    LitePal.deleteAll(MessageInformation.class);
                    for(MessageInformation msg:User.mesList) {
                        MessageInformation temp = new MessageInformation();
                        temp.setMessage(msg.getMessage());
                        temp.setMessageTime(msg.getMessageTime());
                        temp.save();
                    }*/

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
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


}
