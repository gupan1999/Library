package com.example.version1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.version1.Activity.ResultActivity;
import com.example.version1.Model.Book;
import com.example.version1.Model.Decorater;
import com.example.version1.Model.Results;
import com.example.version1.MyApplication;
import com.example.version1.R;
import com.example.version1.Util.HttpManager;
import com.example.version1.Util.HttpUtil;
import com.example.version1.customed.CustomClickListener;
import com.example.version1.zuo.biao.apijson.StringUtil;

import java.util.List;


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
        String level[] = getResources().getStringArray(R.array.array);//资源文件
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
                    Toast.makeText(MyApplication.getContext(), "请输入检索词", Toast.LENGTH_SHORT).show();
                else {
                    final int limit = spinner2.getSelectedItemPosition();
                    intent = new Intent(getActivity(), ResultActivity.class);
                    intent.putExtra("limit", limit);
                    intent.putExtra("key", text.getText().toString());
                    intent.putExtra("type", spinner.getSelectedItemPosition());
                    onHttpResponseListener = new HttpManager.OnHttpResponseListener() {
                        int cnt = -1;

                        @Override
                        public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                            cnt++;

                            if (limit == HttpUtil.ALL_SCHOOL)
                                resultJson = resultJson.replace(HttpUtil.libraries[requestCode], HttpUtil.libraries[0]);
                            Results results = JSON.parseObject(resultJson, Results.class);
                            intent.putExtra("total" + cnt, results.getTotal());
                            List<Decorater> decoraterList = results.getDecoraterList();
                            if (decoraterList != null) {
                                for (Decorater decorater : decoraterList) {
                                    Book book = decorater.getBook();
                                    book.setFrom(requestCode);
                                    HttpUtil.bookList.add(book);
                                }

                            }
                            if (cnt == spinner2.getSelectedItemPosition())
                                startActivity(intent);
                        }
                    };
                    if (limit == HttpUtil.MY_SCHOOL)
                        HttpUtil.getBook(0, text.getText().toString(), spinner.getSelectedItemPosition(), HttpUtil.pgcnt, 0, onHttpResponseListener);
                    else {
                        HttpUtil.getBook(0, text.getText().toString(), spinner.getSelectedItemPosition(), HttpUtil.pgcnt / 2, 0, onHttpResponseListener);
                        HttpUtil.getBook(1, text.getText().toString(), spinner.getSelectedItemPosition(), HttpUtil.pgcnt / 2, 0, onHttpResponseListener);
                    }
                }
            }

            @Override
            protected void onFastClick() {

            }

        });

    }


}