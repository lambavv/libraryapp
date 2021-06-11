package com.libraryapp.javafx;

import com.libraryapp.Context;
import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.models.CustomerBookLinkModel;
import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.javafx.domain.BookTableRow;
import com.libraryapp.javafx.domain.CustomerTableRow;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.libraryapp.Context.EDIT_BOOK_KEY;
import static com.libraryapp.Context.EDIT_CUSTOMER_KEY;
import static com.libraryapp.util.disableButton;
import static com.libraryapp.util.enableButton;
import static java.util.stream.Collectors.toList;

@Component
@FxmlView("mainWindow.fxml")
public class mainController extends BaseController {

    private static String idRegex = "[0-9]+";
    private static String isbnRegex = "ISBN[0-9]{10}";
    private static String BOOK_NOT_RESERVED = "None";

    private static final Logger LOG = LoggerFactory.getLogger(mainController.class);

    @FXML
    private TextField searchBarCustomer;
    @FXML
    private TableView customerTable;
    @FXML
    private TableColumn idColumnCustomer;
    @FXML
    private TableColumn fullNameColumn;
    @FXML
    private TableColumn dobColumn;
    @FXML
    private TableColumn addressColumn;
    @FXML
    private TableColumn reservedColumn;
    @FXML
    private Button searchCustomerButton;
    @FXML
    private Button newButtonCustomer;
    @FXML
    private Button editButtonCustomer;

    @FXML
    private Button searchBookButton;
    @FXML
    private TextField searchBarBook;
    @FXML
    private TableView bookTable;
    @FXML
    private TableColumn idColumnBook;
    @FXML
    private TableColumn titleColumn;
    @FXML
    private TableColumn authorColumn;
    @FXML
    private TableColumn yearColumn;
    @FXML
    private TableColumn isbnColumn;
    @FXML
    private TableColumn reservedByColumn;
    @FXML
    private Button editButtonBook;
    @FXML
    private Button newButtonBook;

    public void initialize() {
        addEventListeners();
        bindTableColumnsWithModels();

    }

    private void addEventListeners() {
        addBookSearchButtonEventListener();
        addCustomerSearchButtonEventListener();
        addSelectedRowEventListener();
        addEditWindowEventListener();
        addCreateNewEventListener();
    }

    private void addBookSearchButtonEventListener() {
        searchBookButton.setOnAction(e -> {
            searchBooksAndUpdateTable();
        });
    }

    private void searchBooksAndUpdateTable() {
        var searchFieldValue = searchBarBook.getText();
        try {
            var books = findBooks(searchFieldValue);
            var bookRows = getBookTableRows(books);
            var tableData = FXCollections.observableArrayList(bookRows);
            bookTable.getItems().setAll(tableData);
        } catch (IOException | InterruptedException err) {
            err.printStackTrace();
        }
    }

    private void addCustomerSearchButtonEventListener() {
        searchCustomerButton.setOnAction(e -> {
            searchCustomersAndUpdateTable();
        });
    }

    private void searchCustomersAndUpdateTable() {
        var searchFieldValue = searchBarCustomer.getText();
        try {
            var customers = findCustomers(searchFieldValue);
            var customerRows = getCustomerTableRows(customers);
            var tableData = FXCollections.observableArrayList(customerRows);
            customerTable.getItems().setAll(tableData);
        } catch (IOException | InterruptedException err) {
            err.printStackTrace();
        }
    }

    private void addSelectedRowEventListener() {
        customerTable.setOnMouseClicked(e -> {
            var selectedCustomer = (CustomerTableRow) customerTable.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) {
                disableButton(editButtonCustomer);
            } else {
                enableButton(editButtonCustomer);
            }
        });

        bookTable.setOnMouseClicked(e -> {
            var selectedBook = (BookTableRow) bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook == null) {
                disableButton(editButtonBook);
            } else {
                enableButton(editButtonBook);
            }
        });
    }

    private void addEditWindowEventListener() {
        editButtonBook.setOnMouseClicked(e -> {
            var selectedBook = (BookTableRow) bookTable.getSelectionModel().getSelectedItem();
            if(selectedBook == null) {
                return;
            }
            Context.sharedData.put(EDIT_BOOK_KEY, selectedBook);
            launchNewWindowWithWindowModality(saveBookController.class);
            Context.sharedData.remove(EDIT_BOOK_KEY);
            searchBooksAndUpdateTable();
            searchCustomersAndUpdateTable();
        });

        editButtonCustomer.setOnMouseClicked(e -> {
            var selectedCustomer = (CustomerTableRow) customerTable.getSelectionModel().getSelectedItem();
            Context.sharedData.put(EDIT_CUSTOMER_KEY, selectedCustomer);
            if(selectedCustomer == null) {
                return;
            }
            launchNewWindowWithWindowModality(saveCustomerController.class);
            Context.sharedData.remove(EDIT_CUSTOMER_KEY);
            searchCustomersAndUpdateTable();
            searchBooksAndUpdateTable();
        });
    }

    private <T> Stage launchNewWindowWithWindowModality(Class<T> controllerClass) {
        var stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Context.mainStage);
        var root = (Parent) Context.fxWeaver.loadView(controllerClass);
        Context.currentStage = stage;
        var scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
        return stage;
    }

    private void addCreateNewEventListener() {
        newButtonBook.setOnMouseClicked(e -> {
            launchNewWindowWithWindowModality(saveBookController.class);
            searchBooksAndUpdateTable();
        });

        newButtonCustomer.setOnMouseClicked(e -> {
            launchNewWindowWithWindowModality(saveCustomerController.class);
            searchCustomersAndUpdateTable();
        });
    }

    private void bindTableColumnsWithModels() {
        this.idColumnBook.setCellValueFactory(new PropertyValueFactory<BookTableRow, String>("id"));
        this.titleColumn.setCellValueFactory(new PropertyValueFactory<BookTableRow, String>("title"));
        this.authorColumn.setCellValueFactory(new PropertyValueFactory<BookTableRow, String>("author"));
        this.yearColumn.setCellValueFactory(new PropertyValueFactory<BookTableRow, String>("year"));
        this.isbnColumn.setCellValueFactory(new PropertyValueFactory<BookTableRow, String>("isbn"));
        this.reservedByColumn.setCellValueFactory(new PropertyValueFactory<BookTableRow, String>("reservedBy"));

        this.idColumnCustomer.setCellValueFactory(new PropertyValueFactory<CustomerTableRow, String>("id"));
        this.fullNameColumn.setCellValueFactory(new PropertyValueFactory<CustomerTableRow, String>("fullName"));
        this.dobColumn.setCellValueFactory(new PropertyValueFactory<CustomerTableRow, String>("dateOfBirth"));
        this.addressColumn.setCellValueFactory(new PropertyValueFactory<CustomerTableRow, String>("address"));
        this.reservedColumn.setCellValueFactory(new PropertyValueFactory<CustomerTableRow, String>("reserved"));
    }

    private List<BookModel> findBooks(String searchString) throws IOException, InterruptedException {
        var endpointUrl = this.endpointUrl;
        if (searchString.isBlank()) {
            endpointUrl = endpointUrl + "/books";
            return List.of(makeGetRequest(endpointUrl, BookModel[].class));
        }
        endpointUrl = endpointUrl + "/books/search?searchQuery=" + searchString;
        return List.of(makeGetRequest(endpointUrl, BookModel[].class));
    }

    private List<CustomerModel> findCustomers(String searchString) throws IOException, InterruptedException {
        var endpointUrl = this.endpointUrl;
        if (searchString.isBlank()) {
            endpointUrl = endpointUrl + "/customers";
            return List.of(makeGetRequest(endpointUrl, CustomerModel[].class));
        }
        endpointUrl = endpointUrl + "/customers/search?searchQuery=" + searchString;
        return List.of(makeGetRequest(endpointUrl, CustomerModel[].class));
    }

    private List<BookTableRow> getBookTableRows(List<BookModel> bookModels) {
        return bookModels.stream().map(this::getBookTableRow).collect(toList());
    }

    private BookTableRow getBookTableRow(BookModel bookModel) {
        var bookTableRow = new BookTableRow()
                .setId(bookModel.getId().toString())
                .setTitle(bookModel.getTitle())
                .setAuthor(bookModel.getAuthor())
                .setIsbn(bookModel.getIsbn())
                .setYear(String.valueOf(bookModel.getYear()))
                .setReservedBy(BOOK_NOT_RESERVED);
        if (bookModel.getReserved()) {
            var endpointUrl = this.endpointUrl + "/book/" + bookModel.getId() + "/reservation";
            try {
                var customerBookLink = makeGetRequest(endpointUrl, CustomerBookLinkModel.class);
                bookTableRow.setReservedBy(customerBookLink.getCustomerId().toString());
            } catch (Exception e) {
                LOG.error("Unable to find customer book links for book. BookId: " + bookModel.getId() + "\n"
                        + e.getMessage());
            }
        }
        return bookTableRow;
    }

    private List<CustomerTableRow> getCustomerTableRows(List<CustomerModel> customerModel) {
        return customerModel.stream().map(this::getCustomerTableRow).collect(toList());
    }

    private CustomerTableRow getCustomerTableRow(CustomerModel customerModel) {
        var customerTableRows = new CustomerTableRow()
                .setId(customerModel.getId().toString())
                .setFullName(customerModel.getFullName())
                .setDateOfBirth(customerModel.getDateOfBirth())
                .setAddress(customerModel.getAddress())
                .setReserved(BOOK_NOT_RESERVED);
        var endpointUrl = this.endpointUrl + "/customer/" + customerModel.getId() + "/reservation";
        try {
            var reservedBookIds = List.of(makeGetRequest(endpointUrl, CustomerBookLinkModel[].class))
                    .stream()
                    .map(CustomerBookLinkModel::getBookId)
                    .collect(toList());
            customerTableRows.setReserved(reservedBookIds.toString());
        } catch (Exception e) {
            LOG.error("Unable to find customer book links for customer. CustomerId: " + customerModel.getId() + "\n"
                    + e.getMessage());
        }
        return customerTableRows;
    }
}
