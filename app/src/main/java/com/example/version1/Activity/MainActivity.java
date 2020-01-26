package com.example.version1.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.version1.MyApplication;
import com.example.version1.R;
import com.example.version1.Util.HttpUtil;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.Model.User;



public class MainActivity extends AppCompatActivity{
    private BottomNavigationView bottomNavigationView;
    private Handler handler;
    private NavController navController;

/*
   //首页底部导航栏，new一个匿名内部类对象用来重写(实现)接口BottomNavigationView.OnNavigationItemSelectedListener的onNavigationItemSelected方法
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //根据item的id选择打开哪个页面
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    Intent intent_search=new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent_search);
                    return true;
                case R.id.navigation_hot:
                    Intent intent_hot=new Intent(MainActivity.this, HotActivity.class);
                    //Intent intent_hot=new Intent(MainActivity.this, WebActivity.class);

                    startActivity(intent_hot);
                    return true;
                case R.id.navigation_categories:
                    Intent intent_categories=new Intent(MainActivity.this, CategoriesActivity.class);
                    startActivity(intent_categories);
                    return true;
                case R.id.navigation_mythings:
                    loadInformation();     //点击"我的"，调用loadInformation()
                    Intent intent_mythings=new Intent(MainActivity.this, MythingsActivity.class);
                    startActivity(intent_mythings);
                    return true;
            }
          return false;
        }
    };
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);   //关联layout/fragment_main.xml的配置


        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    //当加载网络失败执行的逻辑代码
                    case HttpUtil.FAIL:
                        Toast.makeText(MyApplication.getContext(), "网络出现了问题", Toast.LENGTH_SHORT).show();
                        DaoSession daoSession = GreenDaoManager.getInstance().getDaoSession();
                        User.mesList = daoSession.getMessageInformationDao().loadAll();         //从本地数据库 的相应表中拉取上一次保存的数据
                        User.leList =daoSession.getLentInformationDao().loadAll();
                        break;
                }
            }
        };
        HttpUtil.getInformation(handler);
       // BottomNavigationView navigation = findViewById(R.id.navigation); //首页底部导航栏的View
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);  //用上面的匿名内部类对象给View设置回调监听

        bottomNavigationView = findViewById(R.id.navigation);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
             R.id.navigation_search, R.id.navigation_mythings, R.id.navigation_categories,R.id.navigation_hot)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }





/*
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

 */
}




