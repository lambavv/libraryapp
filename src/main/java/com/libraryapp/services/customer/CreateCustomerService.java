package com.libraryapp.services.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.domain.request.CustomerRequest;

@Component
public class CreateCustomerService extends CoreCustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateCustomerService.class);

    CustomerModel createCustomer(CustomerRequest customerRequest) {
        validateCustomerRequest(customerRequest);
        var customerModel = CustomerModel.fromRequest(customerRequest);
        LOG.info("Saving book to db..." + customerModel.toString());
        customerRepository.save(customerModel);
        return customerModel;
    }
}
