package com.libraryapp.services.book;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.request.BookRequest;

@Component
public class BookService {

    @Inject
    GetBookService getBookService;
    @Inject
    CreateBookService createBookService;
    @Inject
    DeleteBookService deleteBookService;
    @Inject
    UpdateBookService updateBookService;

    public BookModel createBook(BookRequest bookRequest) {
        return createBookService.createBook(bookRequest);
    }

    public BookModel getBook(Integer bookId) {
        return getBookService.getBook(bookId);
    }

    public List<BookModel> getBooks(String searchQuery) {
        return getBookService.getBooks(searchQuery);
    }

    public BookModel getBookByIsbn(String isbn) {
        return getBookService.getBookByISBN(isbn);
    }

    public List<BookModel> getBookByTitle(String title) {
        return getBookService.getBookByTitle(title);
    }

    public List<BookModel> getBookByAuthor(String author) {
        return getBookService.getBookByAuthor(author);
    }

    public List<BookModel> getBooks(List<Integer> bookIds) {
        return getBookService.getBooks(bookIds);
    }

    public List<BookModel> getAllBooks() {
        return getBookService.getAllBooks();
    }

    public BookModel deleteBook(Integer bookId) {
        var existingBook = getBook(bookId);
        return deleteBookService.deleteBook(existingBook);
    }

    public BookModel updateBook(Integer bookId, BookRequest bookRequest) {
        var existingBook = getBook(bookId);
        return updateBookService.updateBook(existingBook, bookRequest);
    }
}
