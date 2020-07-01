package com.example.version1;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.example.version1.Model.User;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.manager.ActivityManager;
import com.example.version1.manager.DataManager;
import com.facebook.stetho.Stetho;




public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static MyApplication myApplication;

    private void initLifeCycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                ActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        initLifeCycle();
        GreenDaoManager.getInstance();
        Stetho.initializeWithDefaults(this);


    }
    public static MyApplication getInstance() {
        return myApplication;
    }
    /**获取
     * @return 当前用户id
     */
    public long getCurrentUserId() {
        currentUser = getCurrentUser();
        Log.d(TAG, "getCurrentUserId  currentUserId = " + (currentUser == null ? "null" : currentUser.getId()));
        return currentUser == null ? 0 : currentUser.getId();
    }


    private static User currentUser = null;
    public User getCurrentUser() {
        if (currentUser == null) {
            currentUser = DataManager.getInstance().getCurrentUser();
        }
        return currentUser;
    }

    public void saveCurrentUser(User user) {
        if (user == null) {
            Log.e(TAG, "saveCurrentUser  currentUser == null >> return;");
            return;
        }

        currentUser = user;
        DataManager.getInstance().saveCurrentUser(currentUser);
    }

    public void logout() {
        currentUser = null;
        DataManager.getInstance().saveCurrentUser(currentUser);
    }


    /**判断是否为当前
     * @param userId 用户id
     * @return 判断结果
     */
    public boolean isCurrentUser(long userId) {
        return DataManager.getInstance().isCurrentUser(userId);
    }

    public boolean isLoggedIn() {
        return getCurrentUserId() > 0;
    }


}
