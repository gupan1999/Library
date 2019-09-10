package com.example.version1.Util;

import android.util.Log;
import android.widget.Toast;

import com.example.version1.MyApplication;
import com.example.version1.greendao.DaoSession;
import com.example.version1.greendao.GreenDaoManager;
import com.example.version1.greendao.Information;
import com.example.version1.greendao.LentInformation;
import com.example.version1.greendao.MessageInformation;
import com.example.version1.greendao.User;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;


public class Temp {

    public static List<MessageInformation> getMessageList(List<Information>inList){
        List<MessageInformation>mesList=new ArrayList<MessageInformation>();
        for(Information information:inList){

            MessageInformation messageInformation=information.getMessageInformation(information);
            if(messageInformation.getMessage()!=null&&messageInformation.getMessageTime()!=null) {
                mesList.add(messageInformation);
            }
        }
        return mesList;
    }
    public static List<LentInformation>getLentList(List<Information>inList){
        List<LentInformation>LentList=new ArrayList<LentInformation>();
        for(Information information:inList){
            LentInformation lentInformation=information.getLentInformation(information);
            if(lentInformation.getBookName()!=null&&lentInformation.getLentTime()!=null) {
                LentList.add(lentInformation);
            }
        }
        return LentList;
    }
    /*
    public static void loadInformation(Handler handler) {

    }



    public static void loadInformation() {
        loadInformation(new Handler());
    }*/
    public static <T> T removeRecyclerViewItem(BaseRecyclerAdapter adapter,List<T>dataList){
        T removedThing=dataList.remove(adapter.getPosition());    //移除数据源
        adapter.notifyItemRemoved(adapter.getPosition());  //移除item
        adapter.notifyItemRangeChanged(adapter.getPosition(),adapter.getItemCount());  //正确删除后的动画效果
        return  removedThing;
    }
}
