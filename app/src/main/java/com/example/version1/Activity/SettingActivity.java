package com.example.version1.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.version1.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_setting);
    }
}
