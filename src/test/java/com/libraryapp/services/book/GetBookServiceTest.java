package com.libraryapp.services.book;

import static com.libraryapp.test_utils.TestConstructors.createBookModel;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

import javax.ws.rs.ClientErrorException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.libraryapp.db.h2.repositories.BookRepository;

@RunWith(MockitoJUnitRunner.class)
public class GetBookServiceTest {

    @InjectMocks
    GetBookService getBookService;
    @Mock
    BookRepository bookRepository;

    @Test
    public void getBookTest() {
        var bookId = nextInt();
        var bookModel = createBookModel();
        when(bookRepository.findById(bookId)).thenReturn(bookModel);

        var returnedBook = getBookService.getBook(bookId);
        assertThat(returnedBook, is(notNullValue()));
        assertThat(returnedBook, is(bookModel));
    }

    @Test(expected = ClientErrorException.class)
    public void getBookNotFoundTest() {
        var bookId = nextInt();
        when(bookRepository.findById(bookId)).thenReturn(null);

        getBookService.getBook(bookId);
    }

    @Test
    public void getBooksTest() {
        var bookIds = singletonList(nextInt());
        var bookModels = singletonList(createBookModel());
        when(bookRepository.findByIdIn(bookIds)).thenReturn(bookModels);

        var returnedBooks = getBookService.getBooks(bookIds);
        assertThat(returnedBooks.size(), greaterThan(0));
    }

    @Test(expected = ClientErrorException.class)
    public void getBooksNullTest() {
        var bookIds = singletonList(nextInt());
        var bookModels = asList(createBookModel(), null);
        when(bookRepository.findByIdIn(bookIds)).thenReturn(bookModels);

        getBookService.getBooks(bookIds);
    }

    @Test
    public void getAllBooksTest() {
        var bookModels = singletonList(createBookModel());
        when(bookRepository.findAll()).thenReturn(bookModels);
        var returnedBooks = getBookService.getAllBooks();
        assertThat(returnedBooks.size(), greaterThan(0));
    }
}
