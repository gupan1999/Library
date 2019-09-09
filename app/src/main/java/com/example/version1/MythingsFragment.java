package com.example.version1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.version1.Activity.MessageActivity;
import com.example.version1.Activity.MyLentActivity;

public class MythingsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mythings, container, false);

        return root;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button MyLent = getActivity().findViewById(R.id.imageButton9);   //我的借阅按钮
        Button Message = getActivity().findViewById(R.id.imageButton13);  //消息按钮
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
    }
    }
