package com.libraryapp.services.book;

import static com.libraryapp.test_utils.TestAssertions.fieldsAreEqual;
import static com.libraryapp.test_utils.TestConstructors.createBookRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.libraryapp.db.h2.repositories.BookRepository;

@RunWith(MockitoJUnitRunner.class)
public class CreateBookServiceTest {

    @InjectMocks
    CreateBookService createBookService;
    @Mock
    BookRepository bookRepository;

    @Test
    public void createBookTest() {
        var bookRequest = createBookRequest();

        var createdBook = createBookService.createBook(bookRequest);
        assertThat(createdBook, is(notNullValue()));
        fieldsAreEqual(createdBook, bookRequest);
        verify(bookRepository).save(createdBook);
    }
}
