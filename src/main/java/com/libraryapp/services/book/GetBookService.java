package com.libraryapp.services.book;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.BookModel;

import static com.libraryapp.util.findByLikePattern;

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

    protected BookModel getBookByISBN(String isbn) {
        var book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            LOG.error(String.format("Not Found. Isbn: %s", isbn));
            throw new ClientErrorException(Response.Status.NOT_FOUND);
        }
        return book;
    }

    protected List<BookModel> getBookByTitle(String title) {
        var books = bookRepository.findByTitleLikeIgnoreCase(String.format(findByLikePattern, title));
        if (books == null) {
            LOG.error(String.format("Search by title LIKE failed. Title: %s", title));
            throw new ClientErrorException(Response.Status.NOT_FOUND);
        }
        return books;
    }

    protected List<BookModel> getBookByAuthor(String author) {
        var books = bookRepository.findByAuthorLikeIgnoreCase(String.format(findByLikePattern, author));
        if (books == null) {
            LOG.error(String.format("Search by author LIKE failed. Title: %s", author));
            throw new ClientErrorException(Response.Status.NOT_FOUND);
        }
        return books;
    }

    protected List<BookModel> getBooks(List<Integer> bookIds) {
        var books = bookRepository.findByIdIn(bookIds);
        if (books.stream().anyMatch(Objects::isNull)) {
            LOG.error("One or more requested books do not exist. Book ids: " + bookIds);
            throw new ClientErrorException(Response.Status.NOT_FOUND);
        }
        return books;
    }
}
