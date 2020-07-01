package com.example.version1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.version1.Model.User;
import com.example.version1.MyApplication;
import com.example.version1.R;
import com.example.version1.Util.BaseActivity;
import com.example.version1.Util.HttpUtil;
import com.example.version1.manager.HttpManager;

import java.util.regex.Pattern;

import apijson.JSONResponse;


public class LoginActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Pattern p = Pattern.compile("^\\d{10}$");
                if (!p.matcher(id).matches())
                    Toast.makeText(LoginActivity.this, "id格式错误", Toast.LENGTH_SHORT).show();
                else {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    HttpUtil.login(id, password,  new HttpManager.OnHttpResponseListener() {
                        @Override
                        public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                            JSONResponse response = new JSONResponse(resultJson);
                            User user = response.getObject(User.class);
                            if (!response.isSuccess() ) {
                                switch (response.getCode()) {
                                    case JSONResponse.CODE_NOT_FOUND:
                                        Toast.makeText(LoginActivity.this, "id不存在", Toast.LENGTH_SHORT).show();
                                        loadingProgressBar.setVisibility(View.GONE);
                                        break;
                                    case JSONResponse.CODE_ILLEGAL_ARGUMENT:
                                        Toast.makeText(LoginActivity.this, "账号或密码不合法", Toast.LENGTH_SHORT).show();
                                        loadingProgressBar.setVisibility(View.GONE);
                                        break;
                                    case JSONResponse.CODE_CONDITION_ERROR:
                                        Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                                        loadingProgressBar.setVisibility(View.GONE);
                                        break;
                                    default:
                                        Toast.makeText(LoginActivity.this, "登录失败，请检查网络后重置", Toast.LENGTH_SHORT).show();
                                        loadingProgressBar.setVisibility(View.GONE);
                                        break;
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                MyApplication.getInstance().saveCurrentUser(user);
                                if (!MyApplication.getInstance().isLoggedIn()) {
                                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                setResult(RESULT_OK, new Intent().putExtra("RESULT_LOGINED", MyApplication.getInstance().isLoggedIn()));
                                finish();
                            }
                        }
                    });
                }
            }
        });

    }

}
