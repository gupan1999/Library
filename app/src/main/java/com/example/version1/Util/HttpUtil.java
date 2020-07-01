package com.example.version1.Util;

import android.os.Handler;

import com.example.version1.Model.Book;
import com.example.version1.Model.Collin;
import com.example.version1.Model.LentInformation;
import com.example.version1.MyApplication;
import com.example.version1.manager.HttpManager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import apijson.JSONObject;
import apijson.JSONRequest;



public class HttpUtil {

    public static List<Book> bookList = new ArrayList<>();
    public static List<Collin> collinList = new ArrayList<>();
    public static List<LentInformation> LentinformationinList = new ArrayList<>();

    public static final int pgcnt = 10;

    public static final int MY_SCHOOL = 0;
    public static final int OTHER_SCHOOL1 = 1;
    private static String[] searchKey = {"bookname", "author",  "isbn"};
    public static String[] libraries = {"Book", "Bookother"};
    public static String[] details = {"Collin", "Collinother"};


    public static int page(int total, int cnt) {
        return (total % cnt == 0) ? total / cnt : total / cnt + 1;
    }

    /**
     * 基础URL，这里服务器设置可切换
     */
    public static String URL_BASE = "http://202.182.107.241:9999/";
//    private static String URL_BASE = "http://192.168.0.103:9999/";


    private static final String URL_HEAD = URL_BASE + "head/";
    private static final String URL_GET = URL_BASE + "get/";
    private static final String URL_POST = URL_BASE + "post/";
    private static final String URL_HEADS = URL_BASE + "heads/";
    private static final String URL_GETS = URL_BASE + "gets/";
    private static final String URL_PUT = URL_BASE + "put/";
    private static final String URL_DELETE = URL_BASE + "delete/";

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

    public static void login(String id, String password, HttpManager.OnHttpResponseListener listener) {
        JSONRequest request = new JSONRequest();
        request.put("id", id);
        request.put("password", password);
        request.put("remember",true);
        HttpManager.getInstance().post(URL_BASE + "login/", request, 0, listener);
    }

    /**退出登录
     * @param requestCode
     * @param listener
     */
    public static void logout(int requestCode, HttpManager.OnHttpResponseListener listener) {
        HttpManager.getInstance().post(URL_BASE + "logout/", new JSONRequest(), requestCode, listener);
        //不能在传到服务器之前销毁session
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                HttpManager.getInstance().saveCookie(null);
            }
        }, 500);
    }

    public static void getLend(HttpManager.OnHttpResponseListener listener) {

        JSONRequest request = new JSONRequest();

        JSONRequest item = new JSONRequest();
        item.put("join", "&/Collin/registnumber@,&/Book/bookno@,");

        JSONRequest lendRequest = new JSONRequest();
        lendRequest.put("userid", MyApplication.getInstance().getCurrentUserId());
        lendRequest.put("state", "未归还");
        lendRequest.put("@column", "registno,date");

        item.put("Lend", lendRequest);

        JSONRequest collinRequest = new JSONRequest();
        collinRequest.put("registnumber@", "/Lend/registno");
        collinRequest.put("@column", "bookno,dept,shelfn");

        item.put("Collin", collinRequest);

        JSONRequest bookRequest = new JSONRequest();
        bookRequest.put("bookno@", "/Collin/bookno");
        bookRequest.put("@column", "bookname");

        item.put("Book", bookRequest);

        request.putAll(item.toArray(0, 0));
        get( request, 0, listener);

    }

    public static void getReservedDates(String name,HttpManager.OnHttpResponseListener listener){
        JSONRequest request = new JSONRequest();
        JSONRequest item = new JSONRequest();
        JSONRequest reserve = new JSONRequest();
        reserve.put("name", name);
        reserve.setOrder("start");
        reserve.setColumn("start,end");
        item.put("Reserve", reserve);
        request.putAll(item.toArray(0, 0));
        get(request,0,listener);
    }

    public static void reserve(String reserveName,String startStr,String endStr,HttpManager.OnHttpResponseListener listener){
        JSONRequest request = new JSONRequest();
        JSONRequest reserve = new JSONRequest();
        reserve.put("name", reserveName);
        reserve.put("userid", String.valueOf(MyApplication.getInstance().getCurrentUserId()));
        reserve.put("start", startStr);
        reserve.put("end", endStr);
        request.put("Reserve", reserve);
        request.setTag("Reserve");
        HttpManager.getInstance().post(URL_BASE + "reserve/", request, 0, listener);
    }

    public static void getMyReserves( HttpManager.OnHttpResponseListener listener){
        JSONRequest request = new JSONRequest();
        JSONRequest item = new JSONRequest();
        JSONRequest reserve = new JSONRequest();
        reserve.put("userid",MyApplication.getInstance().getCurrentUserId());
        reserve.setColumn("name,start,end,id");
        reserve.setOrder("start");
        item.put("Reserve", reserve);
        request.putAll(item.toArray(0, 0));
        get(request,0,listener);
    }

    public static void deleteReserve(Long id,HttpManager.OnHttpResponseListener listener){
            JSONRequest request = new JSONRequest();
            JSONRequest reserve = new JSONRequest();
            reserve.put("id", id);
            request.put("Reserve", reserve);
            request.setTag("Reserve");
            delete(request,0,listener);
    }

}


