package com.libraryapp.services.customer;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.domain.request.CustomerRequest;

@Component
public class CustomerService {

    @Inject
    CreateCustomerService createCustomerService;
    @Inject
    GetCustomerService getCustomerService;
    @Inject
    UpdateCustomerService updateCustomerService;
    @Inject
    DeleteCustomerService deleteCustomerService;


    public CustomerModel createCustomer(CustomerRequest customerRequest) {
        return createCustomerService.createCustomer(customerRequest);
    }

    public CustomerModel getCustomer(Integer customerId) {
        return getCustomerService.getCustomer(customerId);
    }

    public List<CustomerModel> getCustomers(String searchString) {
        return getCustomerService.getCustomers(searchString);
    }

    public List<CustomerModel> getAllCustomers() {
        return getCustomerService.getAllCustomers();
    }

    public CustomerModel updateCustomer(Integer customerId, CustomerRequest customerRequest) {
        var customerModel = getCustomer(customerId);
        return updateCustomerService.updateCustomer(customerModel, customerRequest);
    }

    public CustomerModel deleteCustomer(Integer customerId) {
        var customerModel = getCustomer(customerId);
        return deleteCustomerService.deleteCustomer(customerModel);
    }

    public Boolean existsById(Integer customerId) {
        return getCustomerService.existsById(customerId);
    }

}
