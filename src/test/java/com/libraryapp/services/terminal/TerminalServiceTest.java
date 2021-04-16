package com.libraryapp.services.terminal;

import static com.libraryapp.test_utils.TestConstructors.createBookModel;
import static com.libraryapp.test_utils.TestConstructors.createCustomerModel;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.ws.rs.ClientErrorException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.libraryapp.services.book.BookService;
import com.libraryapp.services.customer.CustomerService;

@RunWith(PowerMockRunner.class)
public class TerminalServiceTest {

    private static List<TerminalService.Choice> testChoices;
    private static int choiceNumber;
    private static Integer id = nextInt();

    @InjectMocks
    TerminalService terminalService = new TerminalService() {
        @Override
        public TerminalService.Choice getUserChoice() {
            var choice = testChoices.get(choiceNumber);
            choiceNumber++;
            return choice;
        }

        @Override
        public Integer getValidatedInputId() {
            return id;
        }
    };

    @Mock
    private BookService bookService;
    @Mock
    private CustomerService customerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void openTerminalTestFirstChoiceThenExit() {
        choiceNumber = 0;
        testChoices = asList(TerminalService.Choice.GEN_BOOK, TerminalService.Choice.EXIT);
        terminalService.openTerminal();
        verify(bookService).createBook(any());
    }

    @Test
    public void openTerminalTestSecondChoiceThenExit() {
        choiceNumber = 0;
        testChoices = asList(TerminalService.Choice.GEN_CUSTOMER, TerminalService.Choice.EXIT);
        terminalService.openTerminal();
        verify(customerService).createCustomer(any());
    }

    @Test
    public void openTerminalTestThirdChoiceThenExit() {
        var bookModel = createBookModel();
        choiceNumber = 0;
        testChoices = asList(TerminalService.Choice.GET_BOOK, TerminalService.Choice.EXIT);
        when(bookService.getBook(id)).thenReturn(bookModel);
        terminalService.openTerminal();
        verify(bookService).getBook(id);
    }

    @Test
    public void openTerminalTestThirdChoiceNotFoundThenExit() {
        choiceNumber = 0;
        testChoices = asList(TerminalService.Choice.GET_BOOK, TerminalService.Choice.EXIT);
        when(bookService.getBook(id)).thenThrow(ClientErrorException.class);
        terminalService.openTerminal();
        verify(bookService).getBook(id);
    }

    @Test
    public void openTerminalTestFourthChoiceThenExit() {
        var customerModel = createCustomerModel();
        choiceNumber = 0;
        testChoices = asList(TerminalService.Choice.GET_CUSTOMER, TerminalService.Choice.EXIT);
        when(customerService.getCustomer(id)).thenReturn(customerModel);
        terminalService.openTerminal();
        verify(customerService).getCustomer(id);
    }

    @Test
    public void openTerminalTestFourthChoiceNotFoundThenExit() {
        choiceNumber = 0;
        testChoices = asList(TerminalService.Choice.GET_CUSTOMER, TerminalService.Choice.EXIT);
        when(customerService.getCustomer(id)).thenThrow(ClientErrorException.class);
        terminalService.openTerminal();
        verify(customerService).getCustomer(id);
    }

    @Test
    public void openTerminalTestFifthChoiceThenExit() {
        var bookModels = singletonList(createBookModel());
        choiceNumber = 0;
        testChoices = asList(TerminalService.Choice.GET_ALL_BOOKS, TerminalService.Choice.EXIT);
        when(bookService.getAllBooks()).thenReturn(bookModels);
        terminalService.openTerminal();
        verify(bookService).getAllBooks();
    }

    @Test
    public void openTerminalTestSixthChoiceThenExit() {
        var customerModels = singletonList(createCustomerModel());
        choiceNumber = 0;
        testChoices = asList(TerminalService.Choice.GET_ALL_CUSTOMERS, TerminalService.Choice.EXIT);
        when(customerService.getAllCustomers()).thenReturn(customerModels);
        terminalService.openTerminal();
        verify(customerService).getAllCustomers();
    }

    @Test
    public void openTerminalTestExitTest() {
        choiceNumber = 0;
        testChoices = singletonList(TerminalService.Choice.EXIT);
        terminalService.openTerminal();
        assertThat(choiceNumber, is(1));
        assertEquals(1, choiceNumber);
    }
}
