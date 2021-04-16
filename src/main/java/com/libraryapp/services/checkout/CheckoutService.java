package com.libraryapp.services.checkout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.db.h2.repositories.BookRepository;
import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.services.book.BookService;
import com.libraryapp.services.customer.CustomerService;

@Component
public class CheckoutService {

    @Inject
    CustomerService customerService;
    @Inject
    BookService bookService;
    @Inject
    BookRepository bookRepository;

    private static final Logger LOG = LoggerFactory.getLogger(CheckoutService.class);

    public void checkoutBooks(Integer customerId, List<Integer> bookIds) {
        var books = getBooksAndValidate(bookIds, false);
        var customer = customerService.getCustomer(customerId);
        books.forEach(book -> book.updateReservedBy(customer));
        bookRepository.saveAll(books);
    }

    public void returnBooks(Integer customerId, List<Integer> bookIds) {
        var books = getBooksAndValidate(bookIds, true);
        books.forEach(book -> book.setReserved(false));

        var customer = customerService.getCustomer(customerId);
        validateBooksReservedByCustomer(books, customer, bookIds);
        books.forEach(BookModel::updateBookReturned);
        bookRepository.saveAll(books);
    }

    private List<BookModel> getBooksAndValidate(List<Integer> bookIds, boolean isReturning) {
        var books = bookService.getBooks(bookIds);
        if (isReturning) {
            validateBooksReserved(books, bookIds);
        } else {
            validateBooksNotReserved(books, bookIds);
        }

        return books;
    }

    private void validateBooksNotReserved(List<BookModel> books, List<Integer> bookIds) {
        if (books.stream().anyMatch(BookModel::getReserved)) {
            LOG.error("One or more books is already reserved. BookIds: " + bookIds);
            throw new ClientErrorException(Response.status(Response.Status.CONFLICT).build());
        }
    }

    private void validateBooksReserved(List<BookModel> books, List<Integer> bookIds) {
        if (books.stream().anyMatch(book -> !book.getReserved())) {
            LOG.error("One or more books is not reserved, unable to return. BookIds: " + bookIds);
            throw new ClientErrorException(Response.status(Response.Status.CONFLICT).build());
        }
    }

    private void validateBooksReservedByCustomer(List<BookModel> books, CustomerModel customer, List<Integer> bookIds) {
        if (books.stream().anyMatch(book -> book.getReservedBy() != customer.getId())) {
            LOG.error("One or more books is not reserved by the customer, unable to return. BookIds: " + bookIds
                    + ", customerId: " + customer.getId());
            throw new ClientErrorException(Response.status(Response.Status.CONFLICT).build());
        }
    }
}
