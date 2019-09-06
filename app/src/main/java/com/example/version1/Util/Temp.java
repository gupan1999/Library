package com.example.version1.Util;

import android.util.Log;

import com.example.version1.Information;
import com.example.version1.MessageInformation;

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
}
