package com.libraryapp.services.customer;

import static com.libraryapp.test_utils.TestConstructors.createCustomerModel;
import static com.libraryapp.test_utils.TestConstructors.createCustomerRequest;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.libraryapp.domain.models.CustomerModel;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;
    @Mock
    GetCustomerService getCustomerService;
    @Mock
    CreateCustomerService createCustomerService;
    @Mock
    DeleteCustomerService deleteCustomerService;
    @Mock
    UpdateCustomerService updateCustomerService;

    @Test
    public void createCustomerTest() {
        var customerRequest = createCustomerRequest();
        var customerModel = createCustomerModel();
        when(customerService.createCustomer(customerRequest)).thenReturn(customerModel);
        var createdCustomer = customerService.createCustomer(customerRequest);
        assertThat(createdCustomer, is(customerModel));
        verify(createCustomerService).createCustomer(customerRequest);
    }

    @Test
    public void getCustomerTest() {
        var customerId = nextInt();
        var customerModel = createCustomerModel();
        when(customerService.getCustomer(customerId)).thenReturn(customerModel);
        var returnedCustomer = customerService.getCustomer(customerId);
        assertThat(returnedCustomer, is(customerModel));
        verify(getCustomerService).getCustomer(customerId);
    }

    @Test
    public void getAllCustomersTest() {
        var customers = singletonList(createCustomerModel());
        when(getCustomerService.getAllCustomers()).thenReturn(customers);
        var returnedCustomers = customerService.getAllCustomers();
        assertThat(returnedCustomers, is(customers));
        verify(getCustomerService).getAllCustomers();
    }

    @Test
    public void deleteCustomerTest() {
        var customerId = nextInt();
        var customer = createCustomerModel();
        when(getCustomerService.getCustomer(customerId)).thenReturn(customer);
        when(deleteCustomerService.deleteCustomer(customer)).thenReturn(customer);
        var deletedCustomer = customerService.deleteCustomer(customerId);
        assertThat(deletedCustomer, is(customer));
        verify(getCustomerService).getCustomer(customerId);
        verify(deleteCustomerService).deleteCustomer(customer);
    }

    @Test
    public void updateCustomerTest() {
        var customerId = nextInt();
        var customerModel = createCustomerModel();
        var customerRequest = createCustomerRequest();
        var updatedCustomerModel = CustomerModel.fromRequest(customerRequest);
        when(getCustomerService.getCustomer(customerId)).thenReturn(customerModel);
        when(updateCustomerService.updateCustomer(customerModel, customerRequest)).thenReturn(updatedCustomerModel);
        var updatedCustomer = customerService.updateCustomer(customerId, customerRequest);
        assertThat(updatedCustomer, is(updatedCustomerModel));
        verify(updateCustomerService).updateCustomer(customerModel, customerRequest);
        verify(getCustomerService).getCustomer(customerId);
    }
}
