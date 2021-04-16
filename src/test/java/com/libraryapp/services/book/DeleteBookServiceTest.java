package com.libraryapp.services.book;

import static com.libraryapp.test_utils.TestConstructors.createBookModel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.libraryapp.db.h2.repositories.BookRepository;

@RunWith(MockitoJUnitRunner.class)
public class DeleteBookServiceTest {

    @InjectMocks
    DeleteBookService deleteBookService;
    @Mock
    BookRepository bookRepository;

    @Test
    public void deleteBookTest() {
        var book = createBookModel();

        var deletedBook = deleteBookService.deleteBook(book);
        assertThat(deletedBook, is(book));
        verify(bookRepository).delete(book);
    }
}
