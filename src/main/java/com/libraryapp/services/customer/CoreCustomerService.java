package com.libraryapp.services.customer;

import static java.util.Objects.isNull;

import javax.inject.Inject;

import com.libraryapp.db.h2.repositories.CustomerBookLinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.libraryapp.db.h2.repositories.CustomerRepository;
import com.libraryapp.domain.request.CustomerRequest;

public abstract class CoreCustomerService {

    @Inject
    CustomerRepository customerRepository;
    @Inject
    CustomerBookLinkRepository customerBookLinkRepository;

    private static final Logger LOG = LoggerFactory.getLogger(CoreCustomerService.class);

    protected void validateCustomerRequest(CustomerRequest customerRequest) {
        if(mandatoryFieldsAreNull(customerRequest)) {
            LOG.error(String.format("Incorrect customer request... fields cannot be null. Full Name: %s, Date of Birth: %s, Address: %s",
                    customerRequest.fullName, customerRequest.dateOfBirth, customerRequest.address));
            throw new IllegalArgumentException();
        }
    }

    private Boolean mandatoryFieldsAreNull(CustomerRequest customerRequest) {
        return (isNull(customerRequest.fullName) ||
                isNull(customerRequest.dateOfBirth) ||
                isNull(customerRequest.address));
    }
}
