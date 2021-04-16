package com.libraryapp.services.book;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.BookModel;

@Component
public class GetBookService extends CoreBookService {

    private static final Logger LOG = LoggerFactory.getLogger(GetBookService.class);

    protected List<BookModel> getAllBooks() {
        return bookRepository.findAll();
    }

    protected BookModel getBook(Integer bookId) {
        var book = bookRepository.findById(bookId);
        if (book == null) {
            LOG.error(String.format("Requested book ID does not exist. Book id: %s", bookId));
            throw new ClientErrorException(Response.Status.NOT_FOUND);
        }
        return book;
    }

    protected List<BookModel> getBooks(List<Integer> bookIds) {
        var books = bookRepository.findByIdIn(bookIds);
        books.stream().mapTo
        if (books.stream().anyMatch(Objects::isNull)) {
            LOG.error("One or more requested books do not exist. Book ids: " + bookIds);
            throw new ClientErrorException(Response.Status.NOT_FOUND);
        }
        return books;
    }
}
