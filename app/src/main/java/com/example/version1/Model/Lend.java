package com.example.version1.Model;

import java.io.Serializable;

import apijson.MethodAccess;

/**
 *
 */
@MethodAccess
public class Lend implements Serializable {
    private static final long serialVersionUID = 1L;

    public Lend() {
        super();
    }
    public Lend(long id) {
        this();
        setLendid(id);
    }


    private Long lendid; //
    private Long userid; //
    private String date; //
    private String registno; //
    private String expire; //
    private String state; //


    public Long getLendid() {
        return lendid;
    }
    public Lend setLendid(Long lendid) {
        this.lendid = lendid;
        return this;
    }

    public Long getUserid() {
        return userid;
    }
    public Lend setUserid(Long userid) {
        this.userid = userid;
        return this;
    }

    public String getDate() {
        return date;
    }
    public Lend setDate(String date) {
        this.date = date;
        return this;
    }

    public String getRegistno() {
        return registno;
    }
    public Lend setRegistno(String registno) {
        this.registno = registno;
        return this;
    }

    public String getExpire() {
        return expire;
    }
    public Lend setExpire(String expire) {
        this.expire = expire;
        return this;
    }

    public String getState() {
        return state;
    }
    public Lend setState(String state) {
        this.state = state;
        return this;
    }


}