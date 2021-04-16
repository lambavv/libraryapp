package com.libraryapp.services.customer;

import static com.libraryapp.test_utils.TestAssertions.fieldsAreEqual;
import static com.libraryapp.test_utils.TestConstructors.createCustomerRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.libraryapp.db.h2.repositories.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CreateCustomerServiceTest {

    @InjectMocks
    CreateCustomerService createCustomerService;
    @Mock
    CustomerRepository customerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createCustomerTest() {
        var customerRequest = createCustomerRequest();

        var createdCustomer = createCustomerService.createCustomer(customerRequest);
        assertThat(createdCustomer, is(notNullValue()));
        fieldsAreEqual(createdCustomer, customerRequest);
        verify(customerRepository).save(createdCustomer);
    }
}
