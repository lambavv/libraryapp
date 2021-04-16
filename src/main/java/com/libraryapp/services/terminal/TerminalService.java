package com.libraryapp.services.terminal;

import static java.time.LocalDate.now;
import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

import java.util.Scanner;

import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.libraryapp.domain.request.BookRequest;
import com.libraryapp.domain.request.CustomerRequest;
import com.libraryapp.services.book.BookService;
import com.libraryapp.services.customer.CustomerService;

@Component
public class TerminalService {

    private static final Logger LOG = LoggerFactory.getLogger(TerminalService.class);
    private static Scanner scanner = new Scanner(System.in);

    @Inject
    BookService bookService;
    @Inject
    CustomerService customerService;

    enum Choice {
        GEN_BOOK,
        GEN_CUSTOMER,
        GET_BOOK,
        GET_CUSTOMER,
        GET_ALL_BOOKS,
        GET_ALL_CUSTOMERS,
        EXIT,
        DEFAULT;

        public static Choice getChoiceById(Integer id) {
            if (id > values().length) {
                System.out.println("Sorry, no such option: " + id);
                return DEFAULT;
            }
            return values()[id];
        }
    }

    public void openTerminal() {
        while (true) {
            System.out.println(
                    "Operations: \n"
                            + "1. Generate random book\n"
                            + "2. Generate random customer\n"
                            + "3. Get Book By Id\n"
                            + "4. Get Customer by Id\n"
                            + "5. Get all Books\n"
                            + "6. Get all Customers\n"
                            + "7. Exit"
            );
            var choice = getUserChoice();
            if (choice == Choice.EXIT) {
                System.out.println("Exiting Terminal...");
                break;
            }
            generateEntity(choice);
        }
    }

    public TerminalService.Choice getUserChoice() {
        return Choice.getChoiceById(scanner.nextInt() - 1);
    }

    private void generateEntity(Choice choice) {
        switch (choice) {
            case GEN_BOOK:
                generateBook();
                break;
            case GEN_CUSTOMER:
                generateCustomer();
                break;
            case GET_BOOK:
                getBookById();
                break;
            case GET_CUSTOMER:
                getCustomerById();
                break;
            case GET_ALL_BOOKS:
                getAllBooks();
                break;
            case GET_ALL_CUSTOMERS:
                getAllCustomers();
                break;
            default:
                LOG.warn("Wrong option.");
        }
    }

    private void generateBook() {
        var bookRequest = new BookRequest();
        bookRequest.author = "Test-" + randomAlphabetic(5);
        bookRequest.isbn = "Test-" + randomAlphanumeric(8);
        bookRequest.year = nextInt(4);
        bookRequest.title = "Test-" + randomAlphabetic(5);
        bookService.createBook(bookRequest);
    }

    private void generateCustomer() {
        var customerRequest = new CustomerRequest();
        customerRequest.fullName = "Test-" + randomAlphabetic(5);
        customerRequest.address = "Test-" + randomAlphabetic(5);
        customerRequest.dateOfBirth = now().toString();
        customerService.createCustomer(customerRequest);
    }

    private void getBookById() {
        var id = getValidatedInputId();
        try {
            var book = bookService.getBook(id);
            System.out.print(book.toString());
        } catch (ClientErrorException e) {
            System.out.println("Book not found!");
        }
    }

    private void getCustomerById() {
        var id = getValidatedInputId();
        try {
            var customer = customerService.getCustomer(id);
            System.out.print(customer.toString());
        } catch (ClientErrorException e) {
            System.out.println("Customer not found!");
        }
    }

    protected Integer getValidatedInputId() {
        System.out.println("Enter customer ID: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Error. Try again...");
            scanner = new Scanner(System.in);
        }
        return scanner.nextInt();
    }

    private void getAllBooks() {
        var books =  bookService.getAllBooks();
        books.forEach(book -> System.out.println(book.toString()));
    }

    private void getAllCustomers() {
        var customers =  customerService.getAllCustomers();
        customers.forEach(customer -> System.out.println(customer.toString()));
    }
}
