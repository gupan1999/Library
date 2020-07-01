/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/


package com.example.version1.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.version1.MyApplication;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import apijson.StringUtil;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * HTTP请求管理类
 *
 * @author Lemon
 * @use HttpManager.getInstance().get(...)或HttpManager.getInstance().post(...)  > 在回调方法onHttpRequestSuccess和onHttpRequestError处理HTTP请求结果
 * @must 解决getToken，getResponseCode，getResponseData中的TODO
 */
public class HttpManager {
    private static final String TAG = "HttpManager";


    private Context context;
    private static HttpManager instance;// 单例

    private HttpManager(Context context) {
        this.context = context;

    }

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager(MyApplication.getInstance());
                }
            }
        }
        return instance;
    }


    /**
     * 列表首页页码。有些服务器设置为1，即列表页码从1开始
     */
    public static final int PAGE_NUM_0 = 0;

    public static final String KEY_TOKEN = "token";
    public static final String KEY_COOKIE = "cookie";


    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 网络请求回调接口
     */
    public interface OnHttpResponseListener {
        /**
         * @param requestCode 请求码，自定义，在发起请求的类中可以用requestCode来区分各个请求
         * @param resultJson  服务器返回的Json串
         * @param e           异常
         */
        void onHttpResponse(int requestCode, String resultJson, Exception e);
    }


    /**
     * POST请求
     *
     * @param url_        接口url
     * @param request     请求
     * @param requestCode 请求码，类似onActivityResult中请求码，当同一activity中以实现接口方式发起多个网络请求时，请求结束后都会回调
     *                    {@link OnHttpResponseListener#onHttpResponse(int, String, Exception)}<br/>
     *                    在发起请求的类中可以用requestCode来区分各个请求
     * @param listener
     */
    public void post(final String url_, final com.alibaba.fastjson.JSONObject request
            , final int requestCode, final OnHttpResponseListener listener) {
            new PostTask(url_,request,requestCode,listener).execute();
    }

    static class PostTask extends AsyncTask<Void, Void, Exception>{
        private String url;
        private com.alibaba.fastjson.JSONObject request;
        private int requestCode;
        private OnHttpResponseListener listener;
        private String result;
        public PostTask(String url,com.alibaba.fastjson.JSONObject request,int requestCode,OnHttpResponseListener listener){
            this.url=url;
            this.request=request;
            this.requestCode=requestCode;
            this.listener = listener;
        }

        @Override
        protected Exception doInBackground(Void... params) {

            try {
                String url = StringUtil.getNoBlankString(this.url);

                OkHttpClient client = HttpManager.getInstance().getHttpClient(url);
                if (client == null) {
                    return new Exception(TAG + ".post  AsyncTask.doInBackground  client == null >> return;");
                }
                String body = JSON.toJSONString(request);
                Log.d(TAG, "\n\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n post  url = " + url + "\n request = \n" + body);

                RequestBody requestBody = RequestBody.create(TYPE_JSON, body);

                result = HttpManager.getInstance().getResponseJson(client, new Request.Builder()
                        .addHeader(KEY_TOKEN, HttpManager.getInstance().getToken(url)).url(StringUtil.getNoBlankString(url))
                        .post(requestBody).build());
                Log.d(TAG, "\n post  result = \n" + result + "\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n");
            } catch (Exception e) {
                Log.e(TAG, "post  AsyncTask.doInBackground  try {  result = getResponseJson(..." +
                        "} catch (Exception e) {\n" + e.getMessage());
                return e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Exception exception) {
            super.onPostExecute(exception);
            listener.onHttpResponse(requestCode, result, exception);
        }
    }

    //httpGet/httpPost 内调用方法 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * @param url
     * @return
     */
    private OkHttpClient getHttpClient(String url) {
        Log.i(TAG, "getHttpClient  url = " + url);
        if (!StringUtil.isNotEmpty(url, true) ) {
            Log.e(TAG, "getHttpClient  StringUtil.isNotEmpty(url, true) == false >> return null;");
            return null;
        }

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .cookieJar(new MyCookieJar()).build();


        return client;
    }

    /**
     * @param tag
     * @return
     * @must demo_***改为服务器设定值
     */
    public String getToken(String tag) {
        return context.getSharedPreferences(KEY_TOKEN, Context.MODE_PRIVATE).getString(KEY_TOKEN + tag, "");
    }

    /**
     * @param tag
     * @param value
     */
    public void saveToken(String tag, String value) {
        context.getSharedPreferences(KEY_TOKEN, Context.MODE_PRIVATE)
                .edit()
                .remove(KEY_TOKEN + tag)
                .putString(KEY_TOKEN + tag, value)
                .commit();
    }


    /**
     * @return
     */
    public String getCookie() {
        return context.getSharedPreferences(KEY_COOKIE, Context.MODE_PRIVATE).getString(KEY_COOKIE, "");
    }

    /**
     * @param value
     */
    public void saveCookie(String value) {
        context.getSharedPreferences(KEY_COOKIE, Context.MODE_PRIVATE)
                .edit()
                .remove(KEY_COOKIE)
                .putString(KEY_COOKIE, value)
                .commit();
    }


    /**
     * @param client
     * @param request
     * @return
     * @throws Exception
     */
    private String getResponseJson(OkHttpClient client, Request request) throws Exception {
        if (client == null || request == null) {
            Log.e(TAG, "getResponseJson  client == null || request == null >> return null;");
            return null;
        }
        Response response = client.newCall(request).execute();
        return response.isSuccessful() ? response.body().string() : null;
    }

    /**
     * 从object中获取key对应的值
     * *获取如果T是基本类型容易崩溃，所以需要try-catch
     *
     * @param json
     * @param key
     * @return
     * @throws JSONException
     */
    public <T> T getValue(String json, String key) throws JSONException {
        return getValue(JSON.parseObject(json), key);
    }

    /**
     * 从object中获取key对应的值
     * *获取如果T是基本类型容易崩溃，所以需要try-catch
     *
     * @param object
     * @param key
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(JSONObject object, String key) throws JSONException {
        return (T) object.get(key);
    }

    //httpGet/httpPost 内调用方法 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    /**
     * http请求头
     */
    public class MyCookieJar implements CookieJar {
        public MyCookieJar() {
        }

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            for (Cookie cookie : cookies) {

                Log.d(TAG,"cookie.value(): "+cookie.value()+" cookie.name(): "+cookie.name());
                if (cookie.name().equals("JSESSIONID")) {
                    saveCookie(cookie.value());
                    break;
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            ArrayList<Cookie> cookies = new ArrayList<>();
            Cookie cookie = new Cookie.Builder()
                    .hostOnlyDomain(url.host())
                    .name("JSESSIONID").value(getCookie())
                    .build();
            cookies.add(cookie);
            return cookies;
        }
    }
}


