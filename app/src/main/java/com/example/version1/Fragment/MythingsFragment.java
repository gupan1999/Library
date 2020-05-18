package com.example.version1.Fragment;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.version1.Activity.LoginActivity;
//import com.example.version1.Activity.MessageActivity;
import com.example.version1.Activity.MyElectronicBookshelves;
import com.example.version1.Activity.MyLentActivity;
import com.example.version1.Activity.ReserveActivity;
import com.example.version1.Activity.SettingActivity;
import com.example.version1.Model.Book;
import com.example.version1.Model.Collin;
import com.example.version1.Model.Lend;
import com.example.version1.Model.LentInformation;
import com.example.version1.Model.User;
import com.example.version1.MyApplication;
import com.example.version1.R;
import com.example.version1.Util.HttpUtil;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.manager.DataManager;
import com.example.version1.manager.HttpManager;

import apijson.JSONResponse;

public class MythingsFragment extends Fragment implements HttpManager.OnHttpResponseListener{
    public static final String TAG = "MythingsFragment";
    private Button MyLent;
    private Button Message;
    private Button Setting;
    private Button myElectronicBookshelf;
    private Button Reserve;
    private TextView idView;
    private TextView signView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mythings, container, false);
        MyLent = root.findViewById(R.id.imageButton9);   //我的借阅按钮
        Message = root.findViewById(R.id.imageButton13);  //消息按钮
        Setting =root.findViewById(R.id.imageButton15);
        Reserve = root.findViewById(R.id.reserve);
        myElectronicBookshelf = root.findViewById(R.id.imageButton11);
        idView = root.findViewById(R.id.editText);
        signView = root.findViewById(R.id.editText3);
        return root;
    }

    @Override
    public void onResume() {
        refreshState();
        super.onResume();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signView.setVisibility(View.GONE);
//        Message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (MyApplication.getInstance().isLoggedIn()) {
//                    Intent intent1 = new Intent(getActivity(), MessageActivity.class);
//                    startActivity(intent1);
//                }else {
//                    callForLogin();
//                }
//            }
//        });
        MyLent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isLoggedIn()) {


                    Intent intent2 = new Intent(getActivity(), MyLentActivity.class);
                    startActivity(intent2);
                }else {
                    callForLogin();
                }
            }
        });
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent3 = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent3);

            }
        });
        myElectronicBookshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getInstance().isLoggedIn()) {
                Intent intent=new Intent(getActivity(), MyElectronicBookshelves.class);
                startActivity(intent);
                }else {
                    callForLogin();
                }
            }
        });
        Reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getInstance().isLoggedIn()) {
                    Intent intent = new Intent(getActivity(), ReserveActivity.class);
                    startActivity(intent);
                } else {
                    callForLogin();
                }
            }
        });
        idView.setOnCreateContextMenuListener(MythingsFragment.this);
        refreshState();

    }
    private void callForLogin(){
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
    }

    private void refreshState(){
        if(!MyApplication.getInstance().isLoggedIn()){
            idView.setText("");
        }else{
            Log.d(TAG,String.valueOf(MyApplication.getInstance().getCurrentUserId()));
            idView.setText(String.valueOf(MyApplication.getInstance().getCurrentUserId()));

        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.login:
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                HttpUtil.logout(HTTP_LOUOUT, this);
                DaoSession daoSession = GreenDaoManager.getInstance().getDaoSession();
                daoSession.getLentInformationDao().deleteAll();      //数据库清空旧数据
                DataManager.getInstance().setLastCacheDate(null);

                MyApplication.getInstance().logout();
                    refreshState();
                    Toast.makeText(getContext(),"登出成功",Toast.LENGTH_SHORT).show();

                return true;
        }

        return false;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        if (!MyApplication.getInstance().isLoggedIn()){
            new MenuInflater(getContext()).inflate(R.menu.login,menu);
        }else {
            new MenuInflater(getContext()).inflate(R.menu.logout,menu);
        }


    }

    public static final int HTTP_LOUOUT = 2;
    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        Log.d(TAG, "onHttpResponse  requestCode = " + requestCode + "; resultJson = \n" + resultJson);
        if (e != null) {
            e.printStackTrace();
        }
        switch (requestCode) {
            case HTTP_LOUOUT:
                JSONResponse response = new JSONResponse(resultJson).getJSONResponse(User.class.getSimpleName());
                boolean succeed = JSONResponse.isSuccess(response);
                Log.d(TAG, succeed ? "服务端退出成功" : "服务端退出失败");
                Toast.makeText(getContext(), succeed?"服务端退出成功" : "服务端退出失败", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
