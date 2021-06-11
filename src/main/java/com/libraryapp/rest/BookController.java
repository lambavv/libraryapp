package com.libraryapp.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/books/search")
    public List<BookModel> getBooks(@RequestParam String searchQuery) {
        return bookService.getBooks(searchQuery);
    }

    @PostMapping("/books")
    public BookModel createBook(@RequestBody BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @DeleteMapping("/books/{bookId}")
    public BookModel deleteBook(@PathVariable Integer bookId) {
        return bookService.deleteBook(bookId);
    }

    @PutMapping("/books/{bookId}")
    public BookModel updateBook(@PathVariable Integer bookId,
            @RequestBody BookRequest bookRequest) {
        return bookService.updateBook(bookId, bookRequest);
    }

}
