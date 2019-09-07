package com.example.version1.greendao;


import com.example.version1.Information;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LentInformation{
    private String bookName;
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



    public LentInformation(Information information){
        this.bookName=information.getBookName();
        this.lentTime=information.getLentTime();
    }
    public LentInformation(){ }

    @Generated(hash = 364394851)
    public LentInformation(String bookName, String lentTime, Long id) {
        this.bookName = bookName;
        this.lentTime = lentTime;
        this.id = id;
    }
}
