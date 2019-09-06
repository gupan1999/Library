package com.example.version1;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import org.litepal.LitePal;


//注意：此类用来获取全局context
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        LitePal.initialize(this);
        Stetho.initializeWithDefaults(this);

    }
    public static Context getContext(){
        return context;
    }
}
