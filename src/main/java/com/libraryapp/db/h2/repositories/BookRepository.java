package com.libraryapp.db.h2.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.libraryapp.domain.models.BookModel;

@Repository
public interface BookRepository extends CrudRepository<BookModel, String> {

    List<BookModel> findAll();

    BookModel findById(Integer id);
    
    List<BookModel> findByIdIn(List<Integer> ids);
}
