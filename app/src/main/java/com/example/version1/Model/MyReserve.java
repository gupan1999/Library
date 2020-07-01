package com.example.version1.Model;

import java.io.Serializable;

public class MyReserve implements Serializable {
    private String name;
    private Long id;
    private String start;
    private String end;

    public MyReserve() {
    }

    public MyReserve(String name, Long id, String start, String end) {
        this.name = name;
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
