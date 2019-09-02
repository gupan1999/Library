package com.example.version1;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class TitleLayout extends ConstraintLayout {
    private  ImageButton Return;
    private  TextView Title;
    public TitleLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
        Return = findViewById(R.id.Return);
        Title = findViewById(R.id.title);
        setReturn();
    }

   private  void setReturn(){
        Return.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
   }

   public  void setTitle(String name){
        Title.setText(name);
   }



}
