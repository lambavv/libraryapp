package com.libraryapp.domain.request;

public class CustomerRequest {
    public String fullName;
    public String dateOfBirth;
    public String address;

    public CustomerRequest setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public CustomerRequest setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public CustomerRequest setAddress(String address) {
        this.address = address;
        return this;
    }
}
