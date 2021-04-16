package com.libraryapp.domain.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.libraryapp.domain.request.CustomerRequest;

@Entity
@Table(name = "customers")
public class CustomerModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "full_name")
    String fullName;
    @Column(name = "date_of_birth")
    String dateOfBirth;
    @Column(name = "address")
    String address;


    public static CustomerModel fromRequest(CustomerRequest customerRequest) {
        return new CustomerModel()
                .setFullName(customerRequest.fullName)
                .setDateOfBirth(customerRequest.dateOfBirth)
                .setAddress(customerRequest.address);
    }

    public void updateFields(CustomerRequest customerRequest) {
        this.setFullName(customerRequest.fullName)
                .setDateOfBirth(customerRequest.dateOfBirth)
                .setAddress(customerRequest.address);
    }

    public String toString() {
        return String.format("Id: %d, Full Name: %s, Date Of Birth: %s, Address: %s",
                getId(), getFullName(), getDateOfBirth(), getAddress());
    }

    public String getFullName() {
        return fullName;
    }

    public CustomerModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public CustomerModel setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CustomerModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public int getId() {
        return id;
    }

    public CustomerModel setId(int id) {
        this.id = id;
        return this;
    }
}
