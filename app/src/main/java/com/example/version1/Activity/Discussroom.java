package com.example.version1.Activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.example.version1.R;
import com.example.version1.Util.BaseActivity;

public class Discussroom extends BaseActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_discussroom);

    }
}
