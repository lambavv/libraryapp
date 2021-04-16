package com.libraryapp.models;

import static com.libraryapp.test_utils.TestAssertions.fieldsAreEqual;
import static com.libraryapp.test_utils.TestConstructors.createCustomerModel;
import static com.libraryapp.test_utils.TestConstructors.createCustomerRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.libraryapp.domain.models.CustomerModel;

@RunWith(MockitoJUnitRunner.class)
public class CustomerModelTest {

    @Test
    public void fromRequestTest() {
        var customerRequest = createCustomerRequest();

        var customerModel = CustomerModel.fromRequest(customerRequest);
        fieldsAreEqual(customerModel, customerRequest);
        assertThat(customerModel.getAddress(), is(customerRequest.address));
    }

    @Test
    public void updateFieldsTest() {
        var customerRequest = createCustomerRequest();
        var customerModel = createCustomerModel();
        assertThat(customerModel.getFullName(), is(not(customerRequest.fullName)));
        assertThat(customerModel.getDateOfBirth(), is(not(customerRequest.dateOfBirth)));
        assertThat(customerModel.getAddress(), is(not(customerRequest.address)));
        customerModel.updateFields(customerRequest);
        fieldsAreEqual(customerModel, customerRequest);
    }

}
