package com.libraryapp.test_utils;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.domain.request.BookRequest;
import com.libraryapp.domain.request.CustomerRequest;

public class TestAssertions {

    public static void fieldsAreEqual(BookModel bookModel, BookRequest bookRequest) {
        assertThat(bookModel.getAuthor(), is(bookRequest.author));
        assertThat(bookModel.getTitle(), is(bookRequest.title));
        assertThat(bookModel.getIsbn(), is(bookRequest.isbn));
        assertThat(bookModel.getYear(), is(bookRequest.year));
    }

    public static void fieldsAreEqual(CustomerModel customerModel, CustomerRequest customerRequest) {
        assertThat(customerModel.getAddress(), is(customerRequest.address));
        assertThat(customerModel.getDateOfBirth(), is(customerRequest.dateOfBirth));
        assertThat(customerModel.getFullName(), is(customerRequest.fullName));
    }
}
