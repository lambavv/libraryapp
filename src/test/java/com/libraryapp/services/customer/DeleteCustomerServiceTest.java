package com.libraryapp.services.customer;

import static com.libraryapp.test_utils.TestConstructors.createCustomerModel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
public class DeleteCustomerServiceTest {

    @InjectMocks
    DeleteCustomerService deleteCustomerService;
    @Mock
    CustomerRepository customerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deleteCustomerTest() {
        var customer = createCustomerModel();

        var deletedCustomer = deleteCustomerService.deleteCustomer(customer);
        assertThat(deletedCustomer, is(customer));
        verify(customerRepository).delete(deletedCustomer);
    }
}
