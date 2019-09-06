package com.example.version1;

import org.litepal.crud.LitePalSupport;

public class MessageInformation extends LitePalSupport {
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

    String message;
    String messageTime;


    public MessageInformation(Information information){
        this.message=information.getMessage();
        this.messageTime=information.getMessageTime();
    }
    public MessageInformation(){}

}