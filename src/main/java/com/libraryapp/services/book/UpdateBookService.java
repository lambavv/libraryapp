package com.libraryapp.services.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.request.BookRequest;

@Component
public class UpdateBookService extends CoreBookService  {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateBookService.class);

    protected BookModel updateBook(BookModel bookModel, BookRequest bookRequest) {
        validateBookRequest(bookRequest);
        bookModel.updateFields(bookRequest);
        LOG.info("Saving updated book:" + bookModel.toString());
        bookRepository.save(bookModel);
        return bookModel;
    }
}
