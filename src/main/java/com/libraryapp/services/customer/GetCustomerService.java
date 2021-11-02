package com.libraryapp.services.customer;

import java.util.List;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.CustomerModel;

import static com.libraryapp.utils.util.*;
import static java.util.Collections.singletonList;

@Component
public class GetCustomerService extends CoreCustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(GetCustomerService.class);

    protected List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll();
    }

    protected CustomerModel getCustomer(Integer customerId) {
        var customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            LOG.error(String.format("Customer not found. Customer id: %d", customerId));
            throw new ClientErrorException(Response.Status.NOT_FOUND);
        }
        return customer.get();
    }

    protected List<CustomerModel> getCustomers(String searchString) {
       if(match(REGEX_DIGITS_ONLY, searchString)) {
           var customer = customerRepository.findById(Integer.parseInt(searchString));
           if (customer.isPresent()) {
               return singletonList(customer.get());
           }
       }
       return customerRepository.findByFullNameLikeIgnoreCase(String.format(findByLikePattern, searchString));
    }

    protected Boolean existsById(Integer customerId) {
        return customerRepository.existsById(customerId);
    }
}
