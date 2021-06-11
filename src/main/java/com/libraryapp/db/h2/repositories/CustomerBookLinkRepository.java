package com.libraryapp.db.h2.repositories;


import com.libraryapp.domain.models.CustomerBookLinkModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerBookLinkRepository extends CrudRepository<CustomerBookLinkModel, Integer> {

    CustomerBookLinkModel findByBookId(Integer bookId);
    List<CustomerBookLinkModel> findByCustomerId(Integer customerId);
}
