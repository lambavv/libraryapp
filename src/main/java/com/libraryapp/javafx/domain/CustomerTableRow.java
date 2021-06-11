package com.libraryapp.javafx.domain;

public class CustomerTableRow {

    private String id;
    private String fullName;
    private String dateOfBirth;
    private String address;
    private String reserved;

    public String getId() {
        return id;
    }

    public CustomerTableRow setId(String id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public CustomerTableRow setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public CustomerTableRow setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CustomerTableRow setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getReserved() {
        return reserved;
    }

    public CustomerTableRow setReserved(String reserved) {
        this.reserved = reserved;
        return this;
    }
}
