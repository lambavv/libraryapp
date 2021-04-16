package com.libraryapp.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.domain.request.CustomerRequest;
import com.libraryapp.services.customer.CustomerService;

@RestController
public class CustomerController {

    @Inject
    CustomerService customerService;

    @GetMapping("/customers")
    public List<CustomerModel> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{customerId}")
    public CustomerModel getCustomerById(@PathVariable Integer customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    public CustomerModel createCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.createCustomer(customerRequest);
    }

    @DeleteMapping("/customers/{customerId}/delete")
    public CustomerModel deleteCustomer(@PathVariable Integer customerId) {
        return customerService.deleteCustomer(customerId);
    }

    @PutMapping("/customers/{customerId}/update")
    public CustomerModel updateCustomer(@PathVariable Integer customerId,
            @RequestBody CustomerRequest customerRequest) {
        return customerService.updateCustomer(customerId, customerRequest);
    }
}
