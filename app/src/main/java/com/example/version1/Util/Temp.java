//package com.example.version1.Util;
//
//import com.example.version1.Model.Information;
//import com.example.version1.Model.LentInformation;
//import com.example.version1.Model.MessageInformation;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class Temp {
//
//    public static List<MessageInformation> getMessageList(List<Information>inList){
//        List<MessageInformation>mesList=new ArrayList<MessageInformation>();
//        for(Information information:inList){
//
//            MessageInformation messageInformation=information.getMessageInformation(information);
//            if(messageInformation.getMessage()!=null&&messageInformation.getMessageTime()!=null) {
//                mesList.add(messageInformation);
//            }
//        }
//        return mesList;
//    }
//    public static List<LentInformation>getLentList(List<Information>inList){
//        List<LentInformation>LentList=new ArrayList<LentInformation>();
//        for(Information information:inList){
//            LentInformation lentInformation=information.getLentInformation(information);
//            if(lentInformation.getBookName()!=null&&lentInformation.getLentTime()!=null) {
//                LentList.add(lentInformation);
//            }
//        }
//        return LentList;
//    }
//
//
//    public static <T> T removeRecyclerViewItem(BaseRecyclerAdapter adapter,List<T>dataList){
//        T removedThing=dataList.remove(adapter.getPosition());    //移除数据源
//        adapter.notifyItemRemoved(adapter.getPosition());  //移除item
//        adapter.notifyItemRangeChanged(adapter.getPosition(),adapter.getItemCount());  //正确删除后的动画效果
//        return  removedThing;
//    }
//}
