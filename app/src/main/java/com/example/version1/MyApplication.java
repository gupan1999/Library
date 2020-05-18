package com.example.version1;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.manager.DataManager;
import com.example.version1.Model.User;
import com.facebook.stetho.Stetho;

import apijson.StringUtil;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
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
    /**获取当前用户id
     * @return
     */
    public long getCurrentUserId() {
        currentUser = getCurrentUser();
        Log.d(TAG, "getCurrentUserId  currentUserId = " + (currentUser == null ? "null" : currentUser.getId()));
        return currentUser == null ? 0 : currentUser.getId();
    }
//    /**获取当前用户phone
//     * @return
//     */
//    public String getCurrentUserPhone() {
//        currentUser = getCurrentUser();
//        return currentUser == null ? null : currentUser.getPhone();
//    }


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
//        if (user.getId() <= 0 && StringUtil.isNotEmpty(user.getName(), true) == false) {
//            Log.e(TAG, "saveCurrentUser  user.getId() <= 0" +
//                    " && StringUtil.isNotEmpty(user.getName(), true) == false >> return;");
//            return;
//        }

//        if (currentUser != null && user.getId().equals(currentUser.getId())
//                && StringUtil.isNotEmpty(user.getPhone(), true) == false) {
//            user.setPhone(currentUser.getPhone());
//        }
        currentUser = user;
        DataManager.getInstance().saveCurrentUser(currentUser);
    }

    public int logout() {
        currentUser = null;
        DataManager.getInstance().saveCurrentUser(currentUser);
        return 0;
    }

    /**判断是否为当前用户
     * @param userId
     * @return
     */
    public boolean isCurrentUser(long userId) {
        return DataManager.getInstance().isCurrentUser(userId);
    }

    public boolean isLoggedIn() {
        return getCurrentUserId() > 0;
    }

}
