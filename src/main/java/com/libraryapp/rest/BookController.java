package com.libraryapp.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.request.BookRequest;
import com.libraryapp.services.book.BookService;

@RestController
public class BookController {

    @Inject
    BookService bookService;

    @GetMapping("/books")
    public List<BookModel> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{bookId}")
    public BookModel getBookById(@PathVariable Integer bookId) {
        return bookService.getBook(bookId);
    }

    @PostMapping("/books")
    public BookModel createBook(@RequestBody BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @DeleteMapping("/books/{bookId}/delete")
    public BookModel deleteBook(@PathVariable Integer bookId) {
        return bookService.deleteBook(bookId);
    }

    @PutMapping("/books/{bookId}/update")
    public BookModel updateBook(@PathVariable Integer bookId,
            @RequestBody BookRequest bookRequest) {
        return bookService.updateBook(bookId, bookRequest);
    }

}
