package com.example.version1.Model;

public class ReserveInfo {
    private String reserveName;
    private String info;


    public ReserveInfo(String reserveName, String info) {
        this.reserveName = reserveName;
        this.info = info;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getReserveName() {
        return reserveName;
    }

    public void setReserveName(String reserveName) {
        this.reserveName = reserveName;
    }

}
