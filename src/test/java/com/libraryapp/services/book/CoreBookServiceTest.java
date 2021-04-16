package com.libraryapp.services.book;

import static com.libraryapp.test_utils.TestConstructors.createBookRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CoreBookServiceTest {

    private static class TestCoreBookService extends CoreBookService {
    }

    @InjectMocks
    private TestCoreBookService testCoreBookService;

    @Test
    public void validateBookRequestValidTest() {
        var bookRequest = createBookRequest();
        testCoreBookService.validateBookRequest(bookRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateBookRequestNullAuthorTest() {
        var bookRequest = createBookRequest();
        bookRequest.author = null;
        testCoreBookService.validateBookRequest(bookRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateBookRequestNullTitleTest() {
        var bookRequest = createBookRequest();
        bookRequest.title = null;
        testCoreBookService.validateBookRequest(bookRequest);
    }


    @Test(expected = IllegalArgumentException.class)
    public void validateBookRequestNullYearTest() {
        var bookRequest = createBookRequest();
        bookRequest.year = null;
        testCoreBookService.validateBookRequest(bookRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateBookRequestNullIsbnTest() {
        var bookRequest = createBookRequest();
        bookRequest.isbn = null;
        testCoreBookService.validateBookRequest(bookRequest);
    }
}
