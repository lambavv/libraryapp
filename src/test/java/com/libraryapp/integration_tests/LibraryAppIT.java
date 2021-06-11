package com.libraryapp.integration_tests;

import static com.libraryapp.test_utils.TestAssertions.fieldsAreEqual;
import static com.libraryapp.test_utils.TestConstructors.createBookRequest;
import static com.libraryapp.test_utils.TestConstructors.createCustomerRequest;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryapp.LibraryAppApplication;
import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.domain.request.BookRequest;
import com.libraryapp.domain.request.CustomerRequest;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryAppIT {

    @LocalServerPort
    private int port;

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getAllBooksTest() {
        var response = getAllBooks();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatusCode().value(), is(200));
        System.out.println((response));
    }

    @Test
    public void createBookTest() throws JsonProcessingException {
        var bookRequest = createBookRequest();
        var entity = new HttpEntity<BookRequest>(bookRequest, headers);

        var response = createBook(entity);
        assertThat(response, is(notNullValue()));

        var responseBody = response.getBody();
        var book = objectMapper.readValue(responseBody, BookModel.class);

        assertThat(response.getStatusCode().value(), is(200));
        assertThat(book, is(notNullValue()));
        fieldsAreEqual(book, bookRequest);
    }

    @Test
    public void getBookByIdTest() throws JsonProcessingException {
        var bookRequest = createBookRequest();
        var entity = new HttpEntity<BookRequest>(bookRequest, headers);
        var createBookResponseBody = createBook(entity).getBody();

        var book = objectMapper.readValue(createBookResponseBody, BookModel.class);
        fieldsAreEqual(book, bookRequest);

        var getBookByIdResponse = getBookById(String.valueOf(book.getId()));
        var bookFromGet = objectMapper.readValue(getBookByIdResponse.getBody(), BookModel.class);
        assertThat(getBookByIdResponse.getStatusCode().value(), is(200));
        fieldsAreEqual(bookFromGet, bookRequest);
    }

    @Test
    public void deleteBookByIdTest() throws JsonProcessingException {
        var bookRequest = createBookRequest();
        var entity = new HttpEntity<BookRequest>(bookRequest, headers);
        var createBookResponseBody = createBook(entity).getBody();

        var book = objectMapper.readValue(createBookResponseBody, BookModel.class);
        fieldsAreEqual(book, bookRequest);

        var getBookByIdResponse = getBookById(String.valueOf(book.getId()));
        var bookFromGet = objectMapper.readValue(getBookByIdResponse.getBody(), BookModel.class);
        assertThat(getBookByIdResponse.getStatusCode().value(), is(200));
        fieldsAreEqual(bookFromGet, bookRequest);

        var deletedBookResponse = deleteBookById(String.valueOf(bookFromGet.getId()));
        var bookFromDelete = objectMapper.readValue(getBookByIdResponse.getBody(), BookModel.class);
        assertThat(deletedBookResponse.getStatusCode().value(), is(200));
        fieldsAreEqual(bookFromDelete, bookRequest);
    }

    @Test
    public void updateBookTest() throws JsonProcessingException {
        var bookRequest = createBookRequest();
        var entity = new HttpEntity<BookRequest>(bookRequest, headers);

        var response = createBook(entity);
        assertThat(response, is(notNullValue()));

        var responseBody = response.getBody();
        var book = objectMapper.readValue(responseBody, BookModel.class);

        assertThat(response.getStatusCode().value(), is(200));
        assertThat(book, is(notNullValue()));
        fieldsAreEqual(book, bookRequest);

        var updateRequest = createBookRequest();
        entity = new HttpEntity<BookRequest>(updateRequest, headers);
        var updateResponse = updateBookById(String.valueOf(book.getId()), entity);
        var updatedBook = objectMapper.readValue(updateResponse.getBody(), BookModel.class);
        fieldsAreEqual(updatedBook, updateRequest);
    }

    @Test
    public void getAllCustomersNoCustomersTest() {
        var response = getAllCustomers();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatusCode().value(), is(200));
        System.out.println((response));
    }

    @Test
    public void createCustomerTest() throws JsonProcessingException {
        var customerRequest = createCustomerRequest();
        var entity = new HttpEntity<CustomerRequest>(customerRequest, headers);
        var response = createCustomer(entity);
        var customer = objectMapper.readValue(response.getBody(), CustomerModel.class);
        fieldsAreEqual(customer, customerRequest);
    }

    @Test
    public void getCustomerByIdTest() throws JsonProcessingException {
        var customerRequest = createCustomerRequest();
        var entity = new HttpEntity<CustomerRequest>(customerRequest, headers);
        var response = createCustomer(entity);
        var customer = objectMapper.readValue(response.getBody(), CustomerModel.class);
        fieldsAreEqual(customer, customerRequest);

        var getCustomerByIdResponse = getCustomerById(String.valueOf(customer.getId()));
        var customerFromGet = objectMapper.readValue(getCustomerByIdResponse.getBody(), CustomerModel.class);
        assertThat(getCustomerByIdResponse.getStatusCode().value(), is(200));
        fieldsAreEqual(customerFromGet, customerRequest);
    }

    @Test
    public void deleteCustomerByIdTest() throws JsonProcessingException {
        var customerRequest = createCustomerRequest();
        var entity = new HttpEntity<CustomerRequest>(customerRequest, headers);
        var response = createCustomer(entity);
        var customer = objectMapper.readValue(response.getBody(), CustomerModel.class);
        fieldsAreEqual(customer, customerRequest);

        var getCustomerByIdResponse = getCustomerById(String.valueOf(customer.getId()));
        var customerFromGet = objectMapper.readValue(getCustomerByIdResponse.getBody(), CustomerModel.class);
        assertThat(getCustomerByIdResponse.getStatusCode().value(), is(200));
        fieldsAreEqual(customerFromGet, customerRequest);

        var deletedCustomerResponse = deleteCustomerById(String.valueOf(customerFromGet.getId()));
        var customerFromDelete = objectMapper.readValue(deletedCustomerResponse.getBody(), CustomerModel.class);
        assertThat(deletedCustomerResponse.getStatusCode().value(), is(200));
        fieldsAreEqual(customerFromDelete, customerRequest);
    }

    @Test
    public void updateCustomerTest() throws JsonProcessingException {
        var customerRequest = createCustomerRequest();
        var entity = new HttpEntity<CustomerRequest>(customerRequest, headers);
        var response = createCustomer(entity);
        var customer = objectMapper.readValue(response.getBody(), CustomerModel.class);
        fieldsAreEqual(customer, customerRequest);
        assertThat(response.getStatusCode().value(), is(200));
        assertThat(customer, is(notNullValue()));
        fieldsAreEqual(customer, customerRequest);

        var updateRequest = createCustomerRequest();
        entity = new HttpEntity<CustomerRequest>(updateRequest, headers);
        var updateResponse = updateCustomer(String.valueOf(customer.getId()), entity);
        var updatedCustomer = objectMapper.readValue(updateResponse.getBody(), CustomerModel.class);
        fieldsAreEqual(updatedCustomer, updateRequest);
    }

    @Test
    public void checkoutBooksByIdInTest() throws JsonProcessingException {
        var bookRequest1 = createBookRequest();
        var entity = new HttpEntity<BookRequest>(bookRequest1, headers);
        var bookResponse1 = createBook(entity);
        var book1 = objectMapper.readValue(bookResponse1.getBody(), BookModel.class);
        assertThat(book1.getReserved(), is(false));


        var bookRequest2 = createBookRequest();
        entity = new HttpEntity<>(bookRequest2, headers);
        var bookResponse2 = createBook(entity);
        var book2 = objectMapper.readValue(bookResponse2.getBody(), BookModel.class);
        assertThat(book2.getReserved(), is(false));

        var customerRequest = createCustomerRequest();
        var customerEntity = new HttpEntity<>(customerRequest, headers);
        var response = createCustomer(customerEntity);
        var customer = objectMapper.readValue(response.getBody(), CustomerModel.class);

        var bookIds = asList(book1.getId(), book2.getId());
        var bookIdsEntity = new HttpEntity<>(bookIds, headers);
        var checkoutResponse = checkoutBooks(customer.getId(), bookIdsEntity);
        assertThat(checkoutResponse.getStatusCode().value(), is(200));
    }

    @Test
    public void returnBooksByIdInTest() throws JsonProcessingException {
        var bookRequest1 = createBookRequest();
        var entity = new HttpEntity<BookRequest>(bookRequest1, headers);
        var bookResponse1 = createBook(entity);
        var book1 = objectMapper.readValue(bookResponse1.getBody(), BookModel.class);
        assertThat(book1.getReserved(), is(false));


        var bookRequest2 = createBookRequest();
        entity = new HttpEntity<>(bookRequest2, headers);
        var bookResponse2 = createBook(entity);
        var book2 = objectMapper.readValue(bookResponse2.getBody(), BookModel.class);
        assertThat(book2.getReserved(), is(false));

        var customerRequest = createCustomerRequest();
        var customerEntity = new HttpEntity<>(customerRequest, headers);
        var response = createCustomer(customerEntity);
        var customer = objectMapper.readValue(response.getBody(), CustomerModel.class);

        var bookIds = asList(book1.getId(), book2.getId());
        var bookIdsEntity = new HttpEntity<>(bookIds, headers);
        var checkoutResponse = checkoutBooks(customer.getId(), bookIdsEntity);
        assertThat(checkoutResponse.getStatusCode().value(), is(200));
        var returnResponse = returnBooks(customer.getId(), bookIdsEntity);
        assertThat(returnResponse.getStatusCode().value(), is(200));
    }

    private ResponseEntity<String> getAllBooks() {
        return restTemplate.exchange(
                createURLWithPort("/books"),
                HttpMethod.GET, new HttpEntity<String>(null, headers), String.class);
    }

    private ResponseEntity<String> createBook(HttpEntity<BookRequest> bookRequestEntity) {
        return restTemplate.exchange(
                createURLWithPort("/books"),
                HttpMethod.POST, bookRequestEntity, String.class);
    }

    private ResponseEntity<String> getBookById(String id) {
        return restTemplate.exchange(
                createURLWithPort("/books/{bookId}"),
                HttpMethod.GET, new HttpEntity<String>(null, headers), String.class, id);
    }

    private ResponseEntity<String> deleteBookById(String id) {
        return restTemplate.exchange(
                createURLWithPort("/books/{bookId}/delete"),
                HttpMethod.DELETE, new HttpEntity<String>(null, headers), String.class, id);
    }

    private ResponseEntity<String> updateBookById(String id, HttpEntity<BookRequest> bookRequestEntity) {
        return restTemplate.exchange(
                createURLWithPort("/books/{bookId}/update"),
                HttpMethod.PUT, bookRequestEntity, String.class, id);
    }

    private ResponseEntity<String> getAllCustomers() {
        return restTemplate.exchange(
                createURLWithPort("/customers"),
                HttpMethod.GET, new HttpEntity<String>(null, headers), String.class);
    }

    private ResponseEntity<String> createCustomer(HttpEntity<CustomerRequest> customerRequestEntity) {
        return restTemplate.exchange(
                createURLWithPort("/customers"),
                HttpMethod.POST, customerRequestEntity, String.class);
    }

    private ResponseEntity<String> getCustomerById(String id) {
        return restTemplate.exchange(
                createURLWithPort("/customers/{customerId}"),
                HttpMethod.GET, new HttpEntity<String>(null, headers), String.class, id);
    }

    private ResponseEntity<String> deleteCustomerById(String id) {
        return restTemplate.exchange(
                createURLWithPort("/customers/{customerId}/delete"),
                HttpMethod.DELETE, new HttpEntity<String>(null, headers), String.class, id);
    }

    private ResponseEntity<String> updateCustomer(String id, HttpEntity<CustomerRequest> customerRequestEntity) {
        return restTemplate.exchange(
                createURLWithPort("/customers/{customerId}/update"),
                HttpMethod.PUT, customerRequestEntity, String.class, id);
    }

    private ResponseEntity<String> checkoutBooks(Integer customerId, HttpEntity<List<Integer>> bookIdsEntity) {
        return restTemplate.exchange(
                createURLWithPort("/customers/{customerId}/checkout"),
                HttpMethod.POST, bookIdsEntity, String.class, customerId);
    }

    private ResponseEntity<String> returnBooks(Integer customerId, HttpEntity<List<Integer>> bookIdsEntity) {
        return restTemplate.exchange(
                createURLWithPort("/customers/{customerId}/return"),
                HttpMethod.POST, bookIdsEntity, String.class, customerId);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
