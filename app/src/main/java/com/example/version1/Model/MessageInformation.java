package com.example.version1.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

//@Entity 将我们的java普通类变为一个能够被greenDAO识别的数据库类型的实体类
//@Entity 将我们的java普通类变为一个能够被greenDAO识别的数据库类型的实体类
@Entity
public class MessageInformation {
    @Id(autoincrement = true)
    private Long id;
    private String message;
    private String messageTime;
    public MessageInformation(Information information){
        this.message=information.getMessage();
        this.messageTime=information.getMessageTime();
    }
    @Generated(hash = 518337018)
    public MessageInformation(Long id, String message, String messageTime) {
        this.id = id;
        this.message = message;
        this.messageTime = messageTime;
    }
    @Generated(hash = 1191063846)
    public MessageInformation() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessageTime() {
        return this.messageTime;
    }
    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }


}