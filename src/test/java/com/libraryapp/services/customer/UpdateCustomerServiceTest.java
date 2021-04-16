package com.libraryapp.services.customer;

import static com.libraryapp.test_utils.TestAssertions.fieldsAreEqual;
import static com.libraryapp.test_utils.TestConstructors.createCustomerModel;
import static com.libraryapp.test_utils.TestConstructors.createCustomerRequest;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.libraryapp.db.h2.repositories.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class UpdateCustomerServiceTest {

    @InjectMocks
    UpdateCustomerService updateCustomerService;
    @Mock
    CustomerRepository customerRepository;

    @Test
    public void updateCustomerTest() {
        var customerRequest = createCustomerRequest();
        var customerModel = createCustomerModel();

        var updatedCustomer = updateCustomerService.updateCustomer(customerModel, customerRequest);
        verify(customerRepository).save(updatedCustomer);
        fieldsAreEqual(customerModel, customerRequest);
    }
}
