package com.example.version1.Model;

public class Electronicbook {
    private String bookname;
    private String details;
    private int Imageid;

    public Electronicbook() {
    }

    public Electronicbook(String bookname, String details, int imageid) {
        this.bookname = bookname;
        this.details = details;
        Imageid = imageid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getImageid() {
        return Imageid;
    }

    public void setImageid(int imageid) {
        Imageid = imageid;
    }
}
