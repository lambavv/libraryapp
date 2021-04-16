package com.libraryapp.services.book;

import static com.libraryapp.test_utils.TestConstructors.createBookModel;
import static com.libraryapp.test_utils.TestConstructors.createBookRequest;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.libraryapp.domain.models.BookModel;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    BookService bookService;
    @Mock
    GetBookService getBookService;
    @Mock
    CreateBookService createBookService;
    @Mock
    DeleteBookService deleteBookService;
    @Mock
    UpdateBookService updateBookService;

    @Test
    public void createBookTest() {
        var bookRequest = createBookRequest();
        var bookModel = createBookModel();
        when(bookService.createBook(bookRequest)).thenReturn(bookModel);
        var createdBook = bookService.createBook(bookRequest);
        assertThat(createdBook, is(bookModel));
        verify(createBookService).createBook(bookRequest);
    }

    @Test
    public void getBookTest() {
        var bookId = nextInt();
        var bookModel = createBookModel();
        when(bookService.getBook(bookId)).thenReturn(bookModel);
        var returnedBook = bookService.getBook(bookId);
        assertThat(returnedBook, is(bookModel));
        verify(getBookService).getBook(bookId);
    }

    @Test
    public void getBooksTest() {
        var bookIds = singletonList(nextInt());
        var bookModels = singletonList(createBookModel());
        when(bookService.getBooks(bookIds)).thenReturn(bookModels);
        var returnedBooks = bookService.getBooks(bookIds);
        assertThat(returnedBooks.size(), greaterThan(0));
        verify(getBookService).getBooks(bookIds
        );
    }

    @Test
    public void getAllBooksTest() {
        var books = singletonList(createBookModel());
        when(getBookService.getAllBooks()).thenReturn(books);
        var returnedBooks = bookService.getAllBooks();
        assertThat(returnedBooks, is(books));
        verify(getBookService).getAllBooks();
    }

    @Test
    public void deleteBookTest() {
        var bookId = nextInt();
        var book = createBookModel();
        when(getBookService.getBook(bookId)).thenReturn(book);
        when(deleteBookService.deleteBook(book)).thenReturn(book);
        var deletedBook = bookService.deleteBook(bookId);
        assertThat(deletedBook, is(book));
        verify(getBookService).getBook(bookId);
        verify(deleteBookService).deleteBook(book);
    }

    @Test
    public void updateBookTest() {
        var bookId = nextInt();
        var bookModel = createBookModel();
        var bookRequest = createBookRequest();
        var updatedBookModel = BookModel.fromRequest(bookRequest);
        when(getBookService.getBook(bookId)).thenReturn(bookModel);
        when(updateBookService.updateBook(bookModel, bookRequest)).thenReturn(updatedBookModel);
        var updatedBook = bookService.updateBook(bookId, bookRequest);
        assertThat(updatedBook, is(updatedBookModel));
        verify(updateBookService).updateBook(bookModel, bookRequest);
        verify(getBookService).getBook(bookId);
    }
}
