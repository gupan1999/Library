package com.example.version1.greendao;

//Information接受从服务器请求得到的信息
public class Information {
    private String bookName;
    private String lentTime;
    private String message;
    private String messageTime;
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

