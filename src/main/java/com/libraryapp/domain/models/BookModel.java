package com.libraryapp.domain.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.libraryapp.domain.request.BookRequest;

@Entity
@Table(name = "books")
public class BookModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "publishing_year")
    private int year;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "reserved")
    private Boolean reserved;
    @Column(name = "reserved_by")
    private Integer reservedBy;

    public static BookModel fromRequest(BookRequest bookRequest) {
        return new BookModel()
                .setAuthor(bookRequest.author)
                .setIsbn(bookRequest.isbn)
                .setTitle(bookRequest.title)
                .setYear(bookRequest.year)
                .setReserved(false)
                .setReservedBy(-1);
    }

    public void updateFields(BookRequest bookRequest) {
        this.setAuthor(bookRequest.author)
                .setIsbn(bookRequest.isbn)
                .setTitle(bookRequest.title)
                .setYear(bookRequest.year);
    }

    public String toString() {
        return String.format("Id: %d, Author: %s, Title: %s, ISBN: %s, Year: %d, Reserved: %b",
               getId(), getAuthor(), getTitle(), getIsbn(), getYear(), getReserved());
    }

    public BookModel updateReservedBy(CustomerModel customer) {
        return setReserved(true)
                .setReservedBy(customer.getId());
    }

    public BookModel updateBookReturned() {
        return setReserved(false)
                .setReservedBy(-1);
    }

    public Boolean getReserved() {
        return reserved;
    }

    public BookModel setReserved(Boolean reserved) {
        this.reserved = reserved;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookModel setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public int getYear() {
        return year;
    }

    public BookModel setYear(int year) {
        this.year = year;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getReservedBy() {
        return reservedBy;
    }

    public BookModel setReservedBy(Integer reservedBy) {
        this.reservedBy = reservedBy;
        return this;
    }

    public int getId() {
        return id;
    }

    public BookModel setId(int id) {
        this.id = id;
        return this;
    }
}
