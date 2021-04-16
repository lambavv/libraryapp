package com.libraryapp.services.customer;

import static com.libraryapp.test_utils.TestConstructors.createCustomerRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CoreCustomerServiceTest {

    private static class TestCoreCustomerService extends CoreCustomerService {
    }

    @InjectMocks
    private CoreCustomerServiceTest.TestCoreCustomerService testCoreCustomerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validateCustomerRequestValidTest() {
        var customerRequest = createCustomerRequest();
        testCoreCustomerService.validateCustomerRequest(customerRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateCustomerRequestNullDateOfBirthTest() {
        var customerRequest = createCustomerRequest();
        customerRequest.dateOfBirth = null;
        testCoreCustomerService.validateCustomerRequest(customerRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateCustomerRequestNullFullNameTest() {
        var customerRequest = createCustomerRequest();
        customerRequest.fullName = null;
        testCoreCustomerService.validateCustomerRequest(customerRequest);
    }


    @Test(expected = IllegalArgumentException.class)
    public void validateCustomerRequestNullAddressTest() {
        var customerRequest = createCustomerRequest();
        customerRequest.address = null;
        testCoreCustomerService.validateCustomerRequest(customerRequest);
    }
}
