package com.libraryapp.db.h2.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.libraryapp.domain.models.CustomerModel;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerModel, Integer> {

    List<CustomerModel> findAll();

   List<CustomerModel> findByFullNameLikeIgnoreCase(String fullName);
}
