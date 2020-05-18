package com.example.version1.Model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import apijson.MethodAccess;

@MethodAccess
@Entity
public class LentInformation{
    private String bookName;
    private String location;
    private String lentTime;
    @Id(autoincrement = true)
    private Long id;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public LentInformation(Information information){
//        this.bookName=information.getBookName();
//        this.lentTime=information.getLentTime();
//    }
    public LentInformation(){ }
    public LentInformation(String bookName, String lentTime, String location){
        this.bookName = bookName;
        this.lentTime = lentTime;
        this.location = location;
    }

    @Generated(hash = 1460575843)
    public LentInformation(String bookName, String location, String lentTime, Long id) {
        this.bookName = bookName;
        this.location = location;
        this.lentTime = lentTime;
        this.id = id;
    }
}
