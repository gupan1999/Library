package com.example.version1.Util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.version1.Model.Book;
import com.example.version1.Model.Collin;
import com.example.version1.Model.Information;
import com.example.version1.Model.LentInformation;
import com.example.version1.Model.MessageInformation;
import com.example.version1.Model.User;
import com.example.version1.MyApplication;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.zuo.biao.apijson.JSONObject;
import com.example.version1.zuo.biao.apijson.JSONRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpUtil {
    public static List<Information> informationList;
    public static List<Book> bookList = new ArrayList<Book>();
    public static List<Collin> collinList = new ArrayList<Collin>();
    public static final int pgcnt = 10;
    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    public static final int MY_SCHOOL = 0;
    public static final int ALL_SCHOOL = 1;
    public static final int OTHER_SCHOOL1 = 1;
    public static String[] searchKey = {"bookname", "author", "publishdate", "isbn"};
    public static String[] libraries = {"Book", "Bookother"};
    public static String[] details = {"Collin", "Collinother"};
    //public static int[] pages = {0, 0};
    //public static info[] libraries_page = {new info("Book", 0), new info("Bookother", 0)};
    public static String responseData;
    public static String host = "http://139.180.204.128:80";
    public static List<String> requestjsons = new ArrayList<String>();
    public static String userdata = "/get_userdata.json";
    public static String picture = "";

    public static int page(int total, int cnt) {
        return (total % cnt == 0) ? total / cnt : total / cnt + 1;
    }

    public static void getInformation(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS) //连接超时
                            .readTimeout(5, TimeUnit.SECONDS) //读取超时
                            .writeTimeout(5, TimeUnit.SECONDS).build(); //写超时;    //默认参数的OkHttpClient，可连缀设置各种参数
                    Request request = new Request.Builder().url(host + userdata).build();  //连缀设置url地址的Request对象
                    //Response response = client.newCall(request).execute();  //同步请求
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message message = handler.obtainMessage();
                            message.what = FAIL;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //得到从网上获取资源，转换成我们想要的类型
                            responseData = response.body().string();
                            //通过handler更新UI
                            Message message = handler.obtainMessage();
                            //message.obj=responseData;
                            message.what = SUCCESS;
                            Log.d("Information", responseData);
                            parseWithGSON(responseData);            //通过Gson解析
                            User.mesList = Temp.getMessageList(informationList); //将解析得到的Information List转为需要的两种List
                            User.leList = Temp.getLentList(informationList);
                            DaoSession daoSession = GreenDaoManager.getInstance().getDaoSession();
                            daoSession.getMessageInformationDao().deleteAll();      //数据库清空旧数据
                            daoSession.getLentInformationDao().deleteAll();
                            for (MessageInformation msg : User.mesList) {
                                daoSession.getMessageInformationDao().insertOrReplace(msg); //数据库添加新数据
                            }
                            for (LentInformation lei : User.leList) {
                                daoSession.getLentInformationDao().insertOrReplace(lei);
                            }
                            handler.sendMessage(message);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static void parseWithGSON(String jsonData) {
        Gson gson = new Gson();
        informationList = gson.fromJson(jsonData, new TypeToken<List<Information>>() {
        }.getType());
    }


    private static MyApplication application;
    /**
     * 基础URL，这里服务器设置可切换
     */
    public static String URL_BASE = "http://139.180.204.128:80/";
    //public static String URL_BASE = "http://192.168.0.103:9999/";

    static {
        application = MyApplication.getInstance();
        // URL_BASE = SettingUtil.getCurrentServerAddress();
    }

    public static final String URL_HEAD = URL_BASE + "head/";
    public static final String URL_GET = URL_BASE + "get/";
    public static final String URL_POST = URL_BASE + "post/";
    public static final String URL_HEADS = URL_BASE + "heads/";
    public static final String URL_GETS = URL_BASE + "gets/";
    public static final String URL_PUT = URL_BASE + "put/";
    public static final String URL_DELETE = URL_BASE + "delete/";

    /**
     * @param request
     * @param requestCode
     * @param listener
     */
    public static void head(JSONObject request, int requestCode, HttpManager.OnHttpResponseListener listener) {
        HttpManager.getInstance().post(URL_HEAD, request, requestCode, listener);
    }

    /**
     * @param request
     * @param requestCode
     * @param listener
     */
    public static void get(JSONObject request, int requestCode, HttpManager.OnHttpResponseListener listener) {
        HttpManager.getInstance().post(URL_GET, request, requestCode, listener);
    }

    /**
     * @param request
     * @param requestCode
     * @param listener
     * @must request最外层有tag，部分请求还要currentUserId和对应的password
     */
    public static void post(JSONObject request, int requestCode, HttpManager.OnHttpResponseListener listener) {
        HttpManager.getInstance().post(URL_POST, request, requestCode, listener);
    }

    /**
     * 用POST方法HEAD数据，request和response都非明文，浏览器看不到，用于对安全性要求高的HEAD请求
     *
     * @param request
     * @param requestCode
     * @param listener
     * @must request最外层有tag，部分请求还要currentUserId和对应的password
     */
    public static void heads(JSONObject request, int requestCode, HttpManager.OnHttpResponseListener listener) {
        HttpManager.getInstance().post(URL_HEADS, request, requestCode, listener);
    }

    /**
     * 用POST方法GET数据，request和response都非明文，浏览器看不到，用于对安全性要求高的GET请求
     *
     * @param request
     * @param requestCode
     * @param listener
     * @must request最外层有tag，部分请求还要currentUserId和对应的password
     */
    public static void gets(JSONObject request, int requestCode, HttpManager.OnHttpResponseListener listener) {
        HttpManager.getInstance().post(URL_GETS, request, requestCode, listener);
    }

    /**
     * @param request
     * @param requestCode
     * @param listener
     * @must request最外层有tag，部分请求还要currentUserId和对应的password
     */
    public static void put(JSONObject request, int requestCode, HttpManager.OnHttpResponseListener listener) {
        HttpManager.getInstance().post(URL_PUT, request, requestCode, listener);
    }

    /**
     * @param request
     * @param requestCode
     * @param listener
     * @must request最外层有tag，部分请求还要currentUserId和对应的password
     */
    public static void delete(JSONObject request, int requestCode, HttpManager.OnHttpResponseListener listener) {
        HttpManager.getInstance().post(URL_DELETE, request, requestCode, listener);
    }

    public static void getBook(int libraryCode, String key, int type, int cnt, int page, HttpManager.OnHttpResponseListener listener) {

        JSONRequest request = new JSONRequest();
        JSONRequest item = new JSONRequest();
        JSONRequest bookRequest = new JSONRequest();
        bookRequest.put(searchKey[type] + "$", "%" + key + "%");
        item.put(libraries[libraryCode], bookRequest);
        item.put("query", 2);
        request.putAll(item.toArray(cnt, page));
        request.put("total@", "/[]/total");
        get(request, libraryCode, listener);

    }

    public static void getColl(int libraryCode, String bookno, HttpManager.OnHttpResponseListener listener){

        JSONRequest request = new JSONRequest();
        JSONRequest item = new JSONRequest();
        JSONRequest collinRequest = new JSONRequest();
        collinRequest.put("bookno", bookno);
        item.put(details[libraryCode],collinRequest);
        request.putsAll(item.toArray(0,0));
        get(request,libraryCode,listener);
    }
}


