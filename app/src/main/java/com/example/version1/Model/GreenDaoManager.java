package com.example.version1.Model;


import android.database.sqlite.SQLiteDatabase;

import com.example.version1.MyApplication;

public class GreenDaoManager {
    private static volatile GreenDaoManager mInstance; //单例
    private DaoMaster mDaoMaster;   //以一定的模式管理Dao类的数据库对象
    private DaoSession mDaoSession; //管理制定模式下的所有可用Dao对象

    public GreenDaoManager() {
        //初始化建议放在Application中进行
        if (mInstance == null) {
            //创建数据库"note.db"
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getInstance(), "note.db", null);
            //获取可写数据库
            SQLiteDatabase database = devOpenHelper.getWritableDatabase();
            //获取数据库对象
            mDaoMaster = new DaoMaster(database);
            //获取Dao对象管理者
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            //保证异步处理安全操作
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}