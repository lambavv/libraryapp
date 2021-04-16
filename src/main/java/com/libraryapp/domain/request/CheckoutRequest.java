package com.libraryapp.domain.request;

public class CheckoutRequest {
    String reserved;
    Integer customerId;
    Integer bookId;

    public String getReserved() {
        return reserved;
    }

    public CheckoutRequest setReserved(String reserved) {
        this.reserved = reserved;
        return this;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public CheckoutRequest setCustomerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public Integer getBookId() {
        return bookId;
    }

    public CheckoutRequest setBookId(Integer bookId) {
        this.bookId = bookId;
        return this;
    }
}
