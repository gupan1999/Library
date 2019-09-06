package com.example.version1;

import org.litepal.crud.LitePalSupport;

//Information接受从服务器请求得到的信息
public class Information {
   String bookName;
     String lentTime;
    String message;
    String messageTime;
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getLentTime() {
        return lentTime;
    }

    public void setLentTime(String lentTime) {
        this.lentTime = lentTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
    //将information转换为消息通知
    public MessageInformation getMessageInformation(Information information){
      return new MessageInformation(information);
    }
    //将information转换为借阅信息
    public LentInformation getLentInformation(Information information){
        return  new LentInformation(information);
    }

}

