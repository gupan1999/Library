package com.example.version1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.version1.Activity.ResultActivity;
import com.example.version1.Model.Book;
import com.example.version1.R;
import com.example.version1.Util.HttpUtil;
import com.example.version1.customed.CustomClickListener;
import com.example.version1.manager.HttpManager;

import apijson.StringUtil;


public class MainFragment extends Fragment {
    private Button button;
    private EditText text;
    private Spinner spinner;
    private Spinner spinner2;
    private Intent intent;
    private ArrayAdapter<String> adapter;
    private HttpManager.OnHttpResponseListener onHttpResponseListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        button = root.findViewById(R.id.button);
        text = root.findViewById(R.id.editText2);

        spinner = root.findViewById(R.id.spinner);
        spinner2 = root.findViewById(R.id.spinner2);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item) {
        };
        String[] level = getResources().getStringArray(R.array.array);//资源文件
        for (int i = 0; i < level.length; i++) {
            adapter.add(level[i]);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick() {
                if (StringUtil.isEmpty(text.getText().toString(), true))
                    Toast.makeText(getContext(), "请输入检索词", Toast.LENGTH_SHORT).show();
                else {
                    final int limit = spinner2.getSelectedItemPosition();
                    intent = new Intent(getActivity(), ResultActivity.class);
                    intent.putExtra("limit", limit);
                    intent.putExtra("key", text.getText().toString());
                    intent.putExtra("type", spinner.getSelectedItemPosition());
                    onHttpResponseListener = new HttpManager.OnHttpResponseListener() {
                        @Override
                        public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                            if(resultJson!=null) {
                                if (limit == HttpUtil.OTHER_SCHOOL1)
                                    resultJson = resultJson.replace(HttpUtil.libraries[requestCode], HttpUtil.libraries[0]);
                                JSONObject response = JSON.parseObject(resultJson);
                                int total = response.getIntValue("total");
                                intent.putExtra("total",total);
                                JSONArray List = response.getJSONArray("[]");
                                if (List == null) {
                                    List = new JSONArray();
                                }
                                JSONObject item;

                                for (int i = 0; i < List.size(); i ++) {
                                    item = List.getJSONObject(i);
                                    if (item == null) {
                                        continue;
                                    }
                                    Book book = item.getObject("Book", Book.class);
                                    book.setFrom(requestCode);
                                    HttpUtil.bookList.add(book);
                                }
                                startActivity(intent);
                            }else  Toast.makeText(getContext(), "访问服务器失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    };
                    if (limit == HttpUtil.MY_SCHOOL)
                        HttpUtil.getBook(0, text.getText().toString(), spinner.getSelectedItemPosition(), HttpUtil.pgcnt, 0, onHttpResponseListener);
                    else {
                        HttpUtil.getBook(1, text.getText().toString(), spinner.getSelectedItemPosition(), HttpUtil.pgcnt, 0, onHttpResponseListener);
                    }
                }
            }

            @Override
            protected void onFastClick() {

            }

        });

    }


}