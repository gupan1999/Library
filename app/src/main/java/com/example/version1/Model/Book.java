package com.example.version1.Model;

import com.alibaba.fastjson.annotation.JSONField;

public class Book {
    private int id;
    private String bookName;
    private String author;
    private String publisher;
    private String publishDate;
    private String isbn;
    private String callNumber;
    private String form;
    private String price;
    private String lendn;
    private String colln;
    private String bookno;

    @JSONField(deserialize = false)
    private int from;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public Book() {
    }

    public Book(int id, String bookName, String author, String publisher, String publishDate, String isbn, String callNumber, String form, String price, String lendn, String colln,String bookno) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.isbn = isbn;
        this.callNumber = callNumber;
        this.form = form;
        this.price = price;
        this.lendn = lendn;
        this.colln = colln;
        this.bookno = bookno;
    }

    public String getBookno() {
        return bookno;
    }

    public void setBookno(String bookno) {
        this.bookno = bookno;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getColln() {
        return colln;
    }

    public void setColln(String colln) {
        this.colln = colln;
    }

    public String getLendn() {
        return lendn;
    }

    public void setLendn(String lendn) {
        this.lendn = lendn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
