package com.libraryapp.services.book;

import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.request.BookRequest;

import static com.libraryapp.util.*;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

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
        if(match(REGEX_ISBN_TEN_DIGITS, searchQuery)) {
            return singletonList(getBookByIsbn(searchQuery));
        }
        if (match(REGEX_DIGITS_ONLY, searchQuery)) {
            return singletonList(getBook(Integer.parseInt(searchQuery)));
        }
        var booksFoundByTitle = getBookByTitle(searchQuery);
        var booksFoundByAuthor = getBookByAuthor(searchQuery);
        return Stream.concat(booksFoundByAuthor.stream(), booksFoundByTitle.stream())
                .distinct()
                .collect(toList());
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

    public BookModel updateBook(Integer bookId, BookRequest bookRequest){
        var existingBook = getBook(bookId);
        return updateBookService.updateBook(existingBook, bookRequest);
    }
}
