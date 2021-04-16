package com.libraryapp.test_utils;

import static java.time.LocalDate.now;
import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.domain.request.BookRequest;
import com.libraryapp.domain.request.CustomerRequest;

public class TestConstructors {

    public static CustomerRequest createCustomerRequest() {
        var customerRequest = new CustomerRequest();
        customerRequest.fullName = "Test-" + randomAlphabetic(5);
        customerRequest.address = "Test-" + randomAlphabetic(5);
        customerRequest.dateOfBirth = now().toString();
        return customerRequest;
    }

    public static BookRequest createBookRequest() {
        var bookRequest = new BookRequest();
        bookRequest.author = "Test-" + randomAlphabetic(5);
        bookRequest.isbn = "Test-" + randomAlphanumeric(8);
        bookRequest.year = nextInt(4);
        bookRequest.title = "Test-" + randomAlphabetic(5);
        return bookRequest;
    }

    public static CustomerModel createCustomerModel() {
        return new CustomerModel()
                .setFullName("Test-" + randomAlphabetic(5))
                .setDateOfBirth("Test-" + randomAlphabetic(5))
                .setAddress("Test-" + randomAlphabetic(5))
                .setId(nextInt(3));
    }

    public static BookModel createBookModel() {
        return new BookModel()
                .setTitle("Test-" + randomAlphabetic(5))
                .setAuthor("Test-" + randomAlphabetic(5))
                .setYear(nextInt(10))
                .setReserved(false)
                .setReservedBy(-1)
                .setId(nextInt(3));
    }
}
