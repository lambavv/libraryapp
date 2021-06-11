package com.libraryapp.javafx.domain;

public class BookTableRow {

    private String id;
    private String title;
    private String author;
    private String year;
    private String isbn;
    private String reservedBy;

    public String getId() {
        return id;
    }

    public BookTableRow setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookTableRow setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookTableRow setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getYear() {
        return year;
    }

    public BookTableRow setYear(String year) {
        this.year = year;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookTableRow setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getReservedBy() {
        return reservedBy;
    }

    public BookTableRow setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
        return this;
    }
}
