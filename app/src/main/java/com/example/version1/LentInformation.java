package com.example.version1;



class LentInformation{
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
