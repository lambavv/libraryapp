package com.libraryapp.services.book;

import static com.libraryapp.test_utils.TestAssertions.fieldsAreEqual;
import static com.libraryapp.test_utils.TestConstructors.createBookModel;
import static com.libraryapp.test_utils.TestConstructors.createBookRequest;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.libraryapp.db.h2.repositories.BookRepository;

@RunWith(MockitoJUnitRunner.class)
public class UpdateBookServiceTest {

    @InjectMocks
    UpdateBookService updateBookService;
    @Mock
    BookRepository bookRepository;

    @Test
    public void updateBookTest() {
        var bookRequest = createBookRequest();
        var bookModel = createBookModel();

        var updatedBook = updateBookService.updateBook(bookModel, bookRequest);
        verify(bookRepository).save(updatedBook);
        fieldsAreEqual(updatedBook, bookRequest);
    }
}
