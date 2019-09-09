package com.example.version1;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.version1.Activity.MainActivity;
import com.example.version1.Activity.MessageActivity;
import com.example.version1.Util.HttpUtil;
import com.example.version1.Util.Temp;
import com.example.version1.customed.TitleLayout;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.greendao.User;

public class MainFragment extends Fragment {
    private  Handler handler;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        return root;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button=getActivity().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            }
        });
    }



}