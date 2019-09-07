package com.example.version1;

import android.app.Application;
import android.content.Context;
import com.example.version1.greendao.GreenDaoManager;
import com.facebook.stetho.Stetho;

public class MyApplication extends Application {
    private static Context context;
    private static MyApplication myApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        context=getApplicationContext();
        GreenDaoManager.getInstance();
        Stetho.initializeWithDefaults(this);

    }
    public static Context getContext(){
        return context;
    }
    public static MyApplication getInstance() {
        return myApplication;
    }

}
