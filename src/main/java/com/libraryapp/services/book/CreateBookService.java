package com.libraryapp.services.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.request.BookRequest;

@Component
public class CreateBookService extends CoreBookService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateBookService.class);

    BookModel createBook(BookRequest bookRequest) {
        validateBookRequest(bookRequest);
        var bookModel = BookModel.fromRequest(bookRequest);
        LOG.info("Saving book to db..." + bookModel.toString());
        bookRepository.save(bookModel);
        return bookModel;
    }


}
