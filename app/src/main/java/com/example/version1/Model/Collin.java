package com.example.version1.Model;

import java.io.Serializable;

public class Collin implements Serializable {
    private String dept;
    private String barcode;
    private String callnumber;
    private String registnumber;
    private String Status;
    private String shelfn;
    private String bookno;
    public Collin() {
    }

    public Collin(String dept, String barcode, String callnumber, String registnumber, String status, String shelfn,String bookno) {
        this.dept = dept;
        this.barcode = barcode;
        this.callnumber = callnumber;
        this.registnumber = registnumber;
        this.Status = status;
        this.shelfn = shelfn;
        this.bookno = bookno;
    }
    public String getBookno() {
        return bookno;
    }

    public void setBookno(String bookno) {
        this.bookno = bookno;
    }
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCallnumber() {
        return callnumber;
    }

    public void setCallnumber(String callnumber) {
        this.callnumber = callnumber;
    }

    public String getRegistnumber() {
        return registnumber;
    }

    public void setRegistnumber(String registnumber) {
        this.registnumber = registnumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getShelfn() {
        return shelfn;
    }

    public void setShelfn(String shelfn) {
        this.shelfn = shelfn;
    }
}
