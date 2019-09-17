package com.example.version1.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.version1.Activity.MainActivity;
import com.example.version1.Activity.MessageActivity;
import com.example.version1.Activity.SearchActivity;
import com.example.version1.MyApplication;
import com.example.version1.R;
import com.example.version1.Util.HttpUtil;
import com.example.version1.Util.Temp;
import com.example.version1.customed.TitleLayout;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.greendao.User;

public class MainFragment extends Fragment {
    private Button button;
    private EditText text;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        button = root.findViewById(R.id.button);
        text = root.findViewById(R.id.editText2);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(getActivity(), SearchActivity.class);
             intent.putExtra("floor",text.getText().toString());
             startActivity(intent);
            }
        });


    }
/*
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

 */



}