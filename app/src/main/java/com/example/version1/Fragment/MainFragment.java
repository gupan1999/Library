package com.example.version1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.version1.Activity.ResultActivity;
import com.example.version1.Activity.SearchActivity;
import com.example.version1.MyApplication;
import com.example.version1.R;
import com.example.version1.Util.HttpUtil;



public class MainFragment extends Fragment {
    private Button button;
    private EditText text;
    private Handler handler;
    private Spinner spinner;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        button = root.findViewById(R.id.button);
        text = root.findViewById(R.id.editText2);
        spinner = root.findViewById(R.id.spinner);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Spinner","parent:"+parent.getId()+"\nview:"+view.getId()+"\nposition:"+position+"\n");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //当加载网络失败执行的逻辑代码
                    case HttpUtil.FAIL:
                        Toast.makeText(MyApplication.getContext(), "网络出现了问题", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getActivity(), ResultActivity.class);
                //Intent intent=new Intent(getActivity(), SearchActivity.class);
                //intent.putExtra("floor",text.getText().toString());

                if (text.getText().toString().equals("")) {
                    Toast.makeText(MyApplication.getContext(), "请输入检索词", Toast.LENGTH_SHORT).show();
                }else{
                    handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            //当加载网络失败执行的逻辑代码
                            case HttpUtil.FAIL:
                                Toast.makeText(MyApplication.getContext(), "网络出现了问题", Toast.LENGTH_SHORT).show();
                                break;
                            case HttpUtil.SUCCESS:
                                startActivity(intent);
                                break;
                        }
                    }
                };
                    HttpUtil.queryTitle(handler, text.getText().toString());


                }
            }
        });


    }

}