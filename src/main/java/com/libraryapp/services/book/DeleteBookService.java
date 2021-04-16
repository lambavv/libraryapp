package com.libraryapp.services.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.BookModel;

@Component
public class DeleteBookService extends CoreBookService  {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteBookService.class);

    BookModel deleteBook(BookModel book) {
        LOG.info("Deleting book from DB...");
        bookRepository.delete(book);
        LOG.info("Book deleted:" + book.toString());
        return book;
    }
}

