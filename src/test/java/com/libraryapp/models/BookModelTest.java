package com.libraryapp.models;

import static com.libraryapp.test_utils.TestAssertions.fieldsAreEqual;
import static com.libraryapp.test_utils.TestConstructors.createBookModel;
import static com.libraryapp.test_utils.TestConstructors.createBookRequest;
import static com.libraryapp.test_utils.TestConstructors.createCustomerModel;
import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.libraryapp.domain.models.BookModel;

@RunWith(MockitoJUnitRunner.class)
public class BookModelTest {

    @Test
    public void fromRequestTest() {
        var bookRequest = createBookRequest();

        var bookModel = BookModel.fromRequest(bookRequest);
        fieldsAreEqual(bookModel, bookRequest);
        assertThat(bookModel.getYear(), is(bookRequest.year));
    }

    @Test
    public void updateFieldsTest() {
        var bookRequest = createBookRequest();
        var bookModel = createBookModel();
        assertThat(bookModel.getAuthor(), is(not(bookRequest.author)));
        assertThat(bookModel.getIsbn(), is(not(bookRequest.isbn)));
        assertThat(bookModel.getTitle(), is(not(bookRequest.title)));
        assertThat(bookModel.getYear(), is(not(bookRequest.year)));
        bookModel.updateFields(bookRequest);
        fieldsAreEqual(bookModel, bookRequest);
    }

    @Test
    public void updateReservedByTest() {
        var bookModel = createBookModel();
        var customerModel = createCustomerModel();
        assertThat(bookModel.getReservedBy(), is(not(customerModel.getId())));
        bookModel.updateReservedBy(customerModel);
        assertThat(bookModel.getReservedBy(), is(customerModel.getId()));
    }

    @Test
    public void updateBookReturnedTest() {
        var bookModel = createBookModel().setReserved(true).setReservedBy(nextInt());
        assertThat(bookModel.getReservedBy(), is(not(-1)));
        assertThat(bookModel.getReserved(), is(true));
        bookModel.updateBookReturned();
        assertThat(bookModel.getReservedBy(), is(-1));
        assertThat(bookModel.getReserved(), is(false));
    }
}
