package com.libraryapp.services.book;

import static java.util.Objects.isNull;

import javax.inject.Inject;

import com.libraryapp.db.h2.repositories.CustomerBookLinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.libraryapp.db.h2.repositories.BookRepository;
import com.libraryapp.domain.request.BookRequest;

public abstract class CoreBookService {

    @Inject
    BookRepository bookRepository;
    @Inject
    CustomerBookLinkRepository customerBookLinkRepository;

    private static final Logger LOG = LoggerFactory.getLogger(CoreBookService.class);

    protected void validateBookRequest(BookRequest bookRequest) {
        if(mandatoryFieldsAreNull(bookRequest)) {
            LOG.error(String.format("Incorrect bookRequest... fields cannot be null. Title: %s, Author: %s, Year: %s, ISBN: %s ",
                    bookRequest.title, bookRequest.author, bookRequest.year, bookRequest.isbn));
            throw new IllegalArgumentException();
        }
    }

    private Boolean mandatoryFieldsAreNull(BookRequest bookRequest) {
        return (isNull(bookRequest.author) ||
                isNull(bookRequest.title) ||
                isNull(bookRequest.year) ||
                isNull(bookRequest.isbn));
    }

}
