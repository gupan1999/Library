package com.example.version1.Util;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyInterceptor implements Interceptor {
    private String type;
    public MyInterceptor() {
    }

    public MyInterceptor(String type) {
        this.type = type;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request requestFu = chain.request();
        String url = requestFu.url().toString();
        Request.Builder builder = requestFu.newBuilder();
        builder.addHeader("type",type);
        Request build = builder.url(url).build();
        return chain.proceed(build);
    }
}
