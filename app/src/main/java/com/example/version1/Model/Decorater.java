package com.example.version1.Model;

public class Decorater {
    private Book book;

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
}
