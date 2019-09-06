package com.example.version1;

import org.litepal.crud.LitePalSupport;

class LentInformation extends LitePalSupport {
    String bookName;
    String lentTime;

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



    public LentInformation(Information information){
        this.bookName=information.getBookName();
        this.lentTime=information.getLentTime();
    }
    public LentInformation(){ }
}