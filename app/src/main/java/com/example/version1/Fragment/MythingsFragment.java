package com.example.version1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.version1.Activity.MessageActivity;
import com.example.version1.Activity.MyElectronicBookshelves;
import com.example.version1.Activity.MyLentActivity;
import com.example.version1.Activity.ReserveActivity;
import com.example.version1.Activity.SettingActivity;
import com.example.version1.Model.MyElectronicBookshelf;
import com.example.version1.R;

public class MythingsFragment extends Fragment {
    private Button MyLent;
    private Button Message;
    private Button Setting;
    private Button myElectronicBookshelf;
    private Button Reserve;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mythings, container, false);
        MyLent = root.findViewById(R.id.imageButton9);   //我的借阅按钮
        Message = root.findViewById(R.id.imageButton13);  //消息按钮
        Setting =root.findViewById(R.id.imageButton15);
        Reserve = root.findViewById(R.id.reserve);
        myElectronicBookshelf = root.findViewById(R.id.imageButton11);
        return root;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent1);
            }
        });
        MyLent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), MyLentActivity.class);

                startActivity(intent2);
            }
        });
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent3);
            }
        });
        myElectronicBookshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MyElectronicBookshelves.class);
                startActivity(intent);
            }
        });
        Reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ReserveActivity.class);
                startActivity(intent);
            }
        });
        /*
        if(!User.isLogin){
            Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }else{
            System.out.println("Logined");
        }*/
    }

    }
