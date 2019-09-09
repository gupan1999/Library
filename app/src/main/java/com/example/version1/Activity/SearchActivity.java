package com.example.version1.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.version1.R;
import com.example.version1.customed.TitleLayout;
import com.example.version1.customed.ZoomImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchActivity extends AppCompatActivity {
    private String Path = "http://192.168.0.103/route/floor2.png";

   private ZoomImageView zoomimageView;

    private static final int SUCCESS = 1;
    private static final int FAIL = 2;
     Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //加载网络成功进行UI的更新,处理得到的图片资源
                case SUCCESS:
                    //通过message，拿到字节数组
                    byte[] Picture = (byte[]) msg.obj;
                    //使用BitmapFactory工厂，把字节数组转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                    //通过imageview，设置图片
                    zoomimageView.setImageBitmap(bitmap);

                    break;
                //当加载网络失败执行的逻辑代码
                case FAIL:
                    Toast.makeText(SearchActivity.this, "网络出现了问题", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//单个页面隐藏标题栏
        setContentView(R.layout.activity_nav);
        //TitleLayout titleLayout=findViewById(R.id.titleLayout6);
        //titleLayout.setTitle("查询");
         zoomimageView=findViewById(R.id.imageView);
        Glide.with(this).load(Path).into(zoomimageView);   //Glide库的基本语句，一行完成通过网络加载图片
      //   RequestPicture();

    }

    void RequestPicture(){
        //1.创建一个okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request.Builder对象，设置参数，请求方式如果是Get，就不用设置，默认就是Get
        Request request = new Request.Builder()
                .url(Path)
                .build();
        //3.创建一个Call对象，参数是request对象，发送请求
        Call call = okHttpClient.newCall(request);
        //4.异步请求，请求加入调度

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            Message message=handler.obtainMessage();
            message.what=FAIL;
            handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到从网上获取资源，转换成我们想要的类型
                byte[] Picture_bt = response.body().bytes();
                //通过handler更新UI
                Message message = handler.obtainMessage();
                message.obj = Picture_bt;
                message.what = SUCCESS;
                handler.sendMessage(message);
            }
        });

    }


}
