package com.libraryapp.services.customer;

import java.util.List;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.CustomerModel;

@Component
public class GetCustomerService extends CoreCustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(GetCustomerService.class);

    protected List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll();
    }

    protected CustomerModel getCustomer(Integer customerId) {
        var customer = customerRepository.findById(customerId);
        if (customer == null) {
            LOG.error(String.format("Customer not found. Customer id: %d", customerId));
            throw new ClientErrorException(Response.Status.NOT_FOUND);
        }
        return customer;
    }
}
