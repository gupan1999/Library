package com.example.version1.Util;

import android.util.Log;

import com.example.version1.greendao.Information;
import com.example.version1.greendao.LentInformation;
import com.example.version1.greendao.MessageInformation;

import java.util.ArrayList;
import java.util.List;

public class Temp {

    public static List<MessageInformation> getMessageList(List<Information>inList){
        List<MessageInformation>mesList=new ArrayList<MessageInformation>();
        for(Information information:inList){

            MessageInformation messageInformation=information.getMessageInformation(information);
            if(messageInformation.getMessage()!=null&&messageInformation.getMessageTime()!=null) {
                Log.d("MessageInformation", messageInformation.getMessage());
                Log.d("MessageInformation", messageInformation.getMessageTime());
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
                Log.d("LentInformation", lentInformation.getBookName());
                Log.d("LentInformation", lentInformation.getLentTime());
                LentList.add(lentInformation);
            }
        }
        return LentList;
    }
}
