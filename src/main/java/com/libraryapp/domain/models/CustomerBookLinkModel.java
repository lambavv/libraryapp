package com.libraryapp.domain.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_to_book")
public class CustomerBookLinkModel {

    @Id
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "customer_id")
    private Integer customerId;

    public Integer getCustomerId() {
        return customerId;
    }

    public CustomerBookLinkModel setCustomerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public Integer getBookId() {
        return bookId;
    }

    public CustomerBookLinkModel setBookId(Integer bookId) {
        this.bookId = bookId;
        return this;
    }
}
