package com.libraryapp.domain.request;

public class BookRequest {
    public String title;
    public String author;
    public Integer year;
    public String isbn;
    public boolean reserved;

    public BookRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookRequest setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookRequest setYear(Integer year) {
        this.year = year;
        return this;
    }

    public BookRequest setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookRequest setReserved(boolean reserved) {
        this.reserved = reserved;
        return this;
    }
}
