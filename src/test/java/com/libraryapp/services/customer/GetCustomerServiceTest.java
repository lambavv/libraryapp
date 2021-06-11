package com.libraryapp.services.customer;

import static com.libraryapp.test_utils.TestConstructors.createCustomerModel;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

import javax.ws.rs.ClientErrorException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.libraryapp.db.h2.repositories.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class GetCustomerServiceTest {

    @InjectMocks
    GetCustomerService getCustomerService;
    @Mock
    CustomerRepository customerRepository;

/*
    @Test
    public void getCustomerTest() {
        var customerId = nextInt();
        var customerModel = createCustomerModel();
        when(customerRepository.findById(customerId)).thenReturn(customerModel);

        var returnedCustomer = getCustomerService.getCustomer(customerId);
        assertThat(returnedCustomer, is(notNullValue()));
        assertThat(returnedCustomer, is(customerModel));
    }
*/

    @Test(expected = ClientErrorException.class)
    public void getCustomerNotFoundTest() {
        var customerId = nextInt();
        when(customerRepository.findById(customerId)).thenReturn(null);

        getCustomerService.getCustomer(customerId);
    }

    @Test
    public void getAllCustomersTest() {
        var customerModels = singletonList(createCustomerModel());
        when(customerRepository.findAll()).thenReturn(customerModels);
        var returnedCustomers = getCustomerService.getAllCustomers();
        assertThat(returnedCustomers.size(), greaterThan(0));
    }
}
