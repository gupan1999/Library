package com.example.version1.Model;

public class Decorater {
    private Book book;
    private Collin collin;
    public Decorater() {
    }

    public Decorater(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Collin getCollin() {
        return collin;
    }

    public void setCollin(Collin collin) {
        this.collin = collin;
    }
}
