package com.libraryapp.services.checkout;

import static com.libraryapp.test_utils.TestConstructors.createBookModel;
import static com.libraryapp.test_utils.TestConstructors.createCustomerModel;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.ClientErrorException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.libraryapp.db.h2.repositories.BookRepository;
import com.libraryapp.services.book.BookService;
import com.libraryapp.services.customer.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutServiceTest {

    @InjectMocks
    CheckoutService checkoutService;
    @Mock
    CustomerService customerService;
    @Mock
    BookService bookService;
    @Mock
    BookRepository bookRepository;

    @Test
    public void checkoutBooksTest() {
        var bookIds = singletonList(nextInt());
        var books = singletonList(createBookModel().setId(bookIds.get(0)));
        when(bookService.getBooks(bookIds)).thenReturn(books);

        var customerId = nextInt();
        var customer = createCustomerModel().setId(customerId);
        when(customerService.getCustomer(customerId)).thenReturn(customer);

        checkoutService.checkoutBooks(customerId, bookIds);
        var reservedBooks = singletonList(books.get(0).updateReservedBy(customer));
        verify(bookRepository).saveAll(reservedBooks);
    }

    @Test(expected = ClientErrorException.class)
    public void checkoutBooksAlreadyReservedTest() {
        var bookIds = singletonList(nextInt());
        var books = singletonList(createBookModel().setId(bookIds.get(0)).setReserved(true));
        when(bookService.getBooks(bookIds)).thenReturn(books);

        var customerId = nextInt();

        checkoutService.checkoutBooks(customerId, bookIds);
    }

    @Test
    public void returnBooksTest() {
        var bookIds = singletonList(nextInt());
        var customerId = nextInt();
        var books = singletonList(createBookModel().setId(bookIds.get(0)).setReserved(true).setReservedBy(customerId));
        when(bookService.getBooks(bookIds)).thenReturn(books);

        var customer = createCustomerModel().setId(customerId);
        when(customerService.getCustomer(customerId)).thenReturn(customer);

        checkoutService.returnBooks(customerId, bookIds);
        var returnedBooks = singletonList(books.get(0).updateBookReturned());
        verify(bookRepository).saveAll(returnedBooks);
    }

    @Test(expected = ClientErrorException.class)
    public void returnBooksNotReservedTest() {
        var bookIds = singletonList(nextInt());
        var customerId = nextInt();
        var books = singletonList(createBookModel().setId(bookIds.get(0)).setReserved(false).setReservedBy(customerId));
        when(bookService.getBooks(bookIds)).thenReturn(books);

        checkoutService.returnBooks(customerId, bookIds);
    }

    @Test(expected = ClientErrorException.class)
    public void returnBooksReservedByOtherCustomerTest() {
        var bookIds = singletonList(nextInt());
        var customerId = nextInt();
        var books = singletonList(createBookModel().setId(bookIds.get(0)).setReserved(true).setReservedBy(nextInt()));
        when(bookService.getBooks(bookIds)).thenReturn(books);

        var customer = createCustomerModel().setId(customerId);
        when(customerService.getCustomer(customerId)).thenReturn(customer);

        checkoutService.returnBooks(customerId, bookIds);
    }
}
