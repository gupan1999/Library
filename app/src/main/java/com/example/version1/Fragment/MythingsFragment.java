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
import com.example.version1.Activity.MyLentActivity;
import com.example.version1.R;

public class MythingsFragment extends Fragment {
    private Button MyLent;
    private Button Message;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mythings, container, false);
        MyLent = root.findViewById(R.id.imageButton9);   //我的借阅按钮
        Message = root.findViewById(R.id.imageButton13);  //消息按钮
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
    }
    /*
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    */

    }
