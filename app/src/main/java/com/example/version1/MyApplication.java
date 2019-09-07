package com.example.version1;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.version1.greendao.DaoMaster;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.facebook.stetho.Stetho;

// TODO: 2019/9/7 尝试使用Litepal失败，尚未了解greendao怎么用 

//注意：此类用来获取全局context
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
