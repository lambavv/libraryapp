package com.libraryapp.services.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.domain.request.CustomerRequest;

@Component
public class UpdateCustomerService extends CoreCustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateCustomerService.class);

    protected CustomerModel updateCustomer(CustomerModel customerModel, CustomerRequest customerRequest) {
        validateCustomerRequest(customerRequest);
        customerModel.setId(customerModel.getId());
        customerModel.updateFields(customerRequest);
        LOG.info("Saving updated customer:" + customerModel.toString());
        customerRepository.save(customerModel);
        return customerModel;
    }
}

