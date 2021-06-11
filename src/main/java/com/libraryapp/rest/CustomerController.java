package com.libraryapp.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/customers/search")
    public List<CustomerModel> getCustomers(@RequestParam String searchQuery) {
        return customerService.getCustomers(searchQuery);
    }

    @PostMapping("/customers")
    public CustomerModel createCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.createCustomer(customerRequest);
    }

    @DeleteMapping("/customers/{customerId}")
    public CustomerModel deleteCustomer(@PathVariable Integer customerId) {
        return customerService.deleteCustomer(customerId);
    }

    @PutMapping("/customers/{customerId}")
    public CustomerModel updateCustomer(@PathVariable Integer customerId,
            @RequestBody CustomerRequest customerRequest) {
        return customerService.updateCustomer(customerId, customerRequest);
    }

    @GetMapping("/customer/{customerId}/exists")
    public Boolean existsById(@PathVariable Integer customerId) {
        return customerService.existsById(customerId);
    }
}
