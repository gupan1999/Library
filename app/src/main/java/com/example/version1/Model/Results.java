package com.example.version1.Model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Results{
    private List<Decorater>decoraterList;
    private int code;
    private int total;
    public Results() {
    }

    public Results(List<Decorater> decoraterList, int code, String msg, String sqlpara1, String sqlpara2, String sqlpara3) {
        this.decoraterList = decoraterList;
        this.code = code;
        this.msg = msg;
        this.sqlpara1 = sqlpara1;
        this.sqlpara2 = sqlpara2;
        this.sqlpara3 = sqlpara3;

    }
    @JSONField(name="[]")
    public List<Decorater> getDecoraterList() {
        return decoraterList;
    }
    @JSONField(name="[]")
    public void setDecoraterList(List<Decorater> decoraterList) {
        this.decoraterList = decoraterList;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSqlpara1() {
        return sqlpara1;
    }

    public void setSqlpara1(String sqlpara1) {
        this.sqlpara1 = sqlpara1;
    }

    public String getSqlpara2() {
        return sqlpara2;
    }

    public void setSqlpara2(String sqlpara2) {
        this.sqlpara2 = sqlpara2;
    }

    public String getSqlpara3() {
        return sqlpara3;
    }

    public void setSqlpara3(String sqlpara3) {
        this.sqlpara3 = sqlpara3;
    }

    private String msg;
    private String sqlpara1;
    private String sqlpara2;
    private String sqlpara3;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
