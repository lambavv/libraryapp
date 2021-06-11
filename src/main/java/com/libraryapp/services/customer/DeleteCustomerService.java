package com.libraryapp.services.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.CustomerModel;

@Component
public class DeleteCustomerService extends CoreCustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteCustomerService.class);

    CustomerModel deleteCustomer(CustomerModel customer) {
        LOG.info("Deleting customer from DB...");
        customerRepository.delete(customer);
        LOG.info("customer deleted:" + customer.toString());
        var links = customerBookLinkRepository.findByCustomerId(customer.getId());
        customerBookLinkRepository.deleteAll(links);
        return customer;
    }
}
