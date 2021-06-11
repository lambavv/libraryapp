package com.libraryapp.services.checkout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

import com.libraryapp.db.h2.repositories.CustomerBookLinkRepository;
import com.libraryapp.domain.models.CustomerBookLinkModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.db.h2.repositories.BookRepository;
import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.services.book.BookService;
import com.libraryapp.services.customer.CustomerService;

import static java.util.stream.Collectors.toList;

@Component
public class CheckoutService {

    @Inject
    CustomerService customerService;
    @Inject
    BookService bookService;
    @Inject
    BookRepository bookRepository;
    @Inject
    CustomerBookLinkRepository customerBookLinkRepository;

    private static final Logger LOG = LoggerFactory.getLogger(CheckoutService.class);

    public CustomerBookLinkModel getCustomerBookLink(Integer bookId) {
        return customerBookLinkRepository.findByBookId(bookId);
    }

    public List<CustomerBookLinkModel> getCustomerBookLinks(Integer customerId) {
        return customerBookLinkRepository.findByCustomerId(customerId);
    }

    public void createCustomerBookLink(Integer bookId, Integer customerId) {
        var customerBookLink = new CustomerBookLinkModel()
                .setBookId(bookId)
                .setCustomerId(customerId);
        customerBookLinkRepository.save(customerBookLink);
    }

    public void checkoutBooks(Integer customerId, List<Integer> bookIds) {
        var books = bookService.getBooks(bookIds);
        books.forEach(book -> createCustomerBookLink(book.getId(), customerId));
        books.forEach(book -> book.setReserved(true));
        bookRepository.saveAll(books);
    }

    public boolean existsBy(Integer bookId) {
        return customerBookLinkRepository.existsById(bookId);
    }

    public void returnBooks(Integer customerId, List<Integer> bookIds) {
        var books = bookService.getBooks(bookIds);
        books.forEach(book -> book.setReserved(false));

        var customerBookLinks = customerBookLinkRepository.findByCustomerId(customerId);
        validateBooksReservedByCustomer(customerBookLinks, customerId, bookIds);
        books.forEach(BookModel::updateBookReturned);
        var returnedBookLinks = customerBookLinks.stream()
                .filter(link -> bookIds.contains(link.getBookId()))
                .collect(toList());
        customerBookLinkRepository.deleteAll(returnedBookLinks);
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

    private void validateBooksReservedByCustomer(List<CustomerBookLinkModel> checkedOutBooks, Integer customerId, List<Integer> bookIds) {
        var checkedOutBookIds = checkedOutBooks.stream()
                .map(CustomerBookLinkModel::getBookId)
                .collect(toList());
        if (!checkedOutBookIds.containsAll(bookIds)) {
            LOG.error("One or more books is not reserved by the customer, unable to return. BookIds: " + bookIds
                    + ", customerId: " + customerId);
            throw new ClientErrorException(Response.status(Response.Status.CONFLICT).build());
        }
    }
}
