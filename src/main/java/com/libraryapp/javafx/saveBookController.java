package com.libraryapp.javafx;

import com.libraryapp.Context;
import com.libraryapp.domain.models.BookModel;
import com.libraryapp.domain.request.BookRequest;
import com.libraryapp.javafx.domain.BookTableRow;
import com.sun.research.ws.wadl.Response;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.libraryapp.Context.EDIT_BOOK_KEY;
import static com.libraryapp.util.*;
import static java.util.Collections.singletonList;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
@FxmlView("saveBookWindow.fxml")
public class saveBookController extends BaseController {

    @Value("${server.url}")
    private String endpointUrl;

    private static final Logger LOG = LoggerFactory.getLogger(mainController.class);

    @FXML
    private TextField bookTitleInput;
    @FXML
    private TextField bookAuthorInput;
    @FXML
    private TextField isbnInput;
    @FXML
    private TextField yearInput;
    @FXML
    private TextField reservedByInput;

    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;

    @FXML
    private Text customerNotFoundWarning;
    @FXML
    private Text yearWarning;
    @FXML
    private Text isbnWarning;

    private boolean editing;
    private String originalReservedById;

    public void initialize() {
        originalReservedById = null;
        editing = false;
        disableButton(saveButton);
        disableButton(deleteButton);
        clearFields();
        if (Context.sharedData.containsKey(EDIT_BOOK_KEY) && Context.sharedData.get(EDIT_BOOK_KEY) != null) {
            editing = true;
            var book = (BookTableRow) Context.sharedData.get(EDIT_BOOK_KEY);
            updateFieldValues(book);
            enableButton(saveButton);
            enableButton(deleteButton);
        }
        addEmptyFieldSaveButtonValidation();
        addCancelButtonListener();
        addSaveButtonListener();
        addDeleteButtonListener();
    }

    private void updateFieldValues(BookTableRow bookTableRow) {
        bookTitleInput.setText(bookTableRow.getTitle());
        bookAuthorInput.setText(bookTableRow.getAuthor());
        yearInput.setText(bookTableRow.getYear());
        isbnInput.setText(bookTableRow.getIsbn());
        if (!bookTableRow.getReservedBy().equals("None") && match(REGEX_DIGITS_ONLY, bookTableRow.getReservedBy())) {
            originalReservedById = bookTableRow.getReservedBy();
            reservedByInput.setText(bookTableRow.getReservedBy());
        }
    }

    private void addEmptyFieldSaveButtonValidation() {
        addListenerToInputToEnableSaveButton(bookTitleInput);
        addListenerToInputToEnableSaveButton(bookAuthorInput);
        addListenerToInputToEnableSaveButton(yearInput);
        addListenerToInputToEnableSaveButton(isbnInput);
    }

    private void addListenerToInputToEnableSaveButton(TextField input) {
        input.setOnKeyReleased(e -> {
            hideText(yearWarning);
            hideText(isbnWarning);
            hideText(customerNotFoundWarning);
            if (input.getText().isBlank()) {
                disableButton(saveButton);
            } else if (allFieldsHaveText()) {
                enableButton(saveButton);
            }
        });
    }

    private boolean allFieldsHaveText() {
        return !bookTitleInput.getText().isBlank()
                && !bookAuthorInput.getText().isBlank()
                && !yearInput.getText().isBlank()
                && !isbnInput.getText().isBlank();
    }

    private void addCancelButtonListener() {
        cancelButton.setOnMouseClicked(e -> {
            Context.currentStage.close();
            Context.currentStage = null;
        });
    }

    private void addSaveButtonListener() {
        saveButton.setOnMouseClicked(e -> {
            if (!match(REGEX_DIGITS_ONLY, yearInput.getText())) {
                showText(yearWarning);
                return;
            }
            if (!match(REGEX_TEN_DIGITS, isbnInput.getText())) {
                showText(isbnWarning);
                return;
            }
            var reservedBy = reservedByInput.getText();
            if (!isBlank(reservedBy) && (!match(REGEX_DIGITS_ONLY, reservedByInput.getText()) || !findCustomer(reservedBy))) {
                showText(customerNotFoundWarning);
                return;
            }
            saveBook();
            Context.currentStage.close();
            Context.currentStage = null;
        });
    }

    private void saveBook() {
        var bookRequest = new BookRequest()
                .setTitle(bookTitleInput.getText())
                .setAuthor(bookAuthorInput.getText())
                .setYear(Integer.parseInt(yearInput.getText()))
                .setIsbn(isbnInput.getText())
                .setReserved(false);
        if(!isBlank(reservedByInput.getText())) {
            bookRequest.setReserved(true);
        }
        var endpointUrl = this.endpointUrl;
        String bookId = null;
        if (editing) {
            var book = (BookTableRow) Context.sharedData.get(EDIT_BOOK_KEY);
            bookId = book.getId();
            endpointUrl = endpointUrl + "/books/" + bookId;
            try {
                var resp = makePutRequest(endpointUrl, BookModel.class, bookRequest);
                LOG.info("New book successfully updated. Id:" + resp.getId());
            } catch (Exception e) {
                LOG.error("Unable to update the book...");
            }
        } else {
            endpointUrl = endpointUrl + "/books";
            try {
                var resp = makePostRequest(endpointUrl, BookModel.class, bookRequest);
                bookId = String.valueOf(resp.getId());
                LOG.info("New book successfully created. Id:" + bookId);
            } catch (Exception e) {
                LOG.error("Unable to create the book...");
            }
        }
        if (!isBlank(bookId)) {
            saveLink(bookId);
        }
    }

    private void saveLink(String bookId) {
        var existsByIdEndpoint = this.endpointUrl + "/customers/book/" + bookId;
        try {
            var linkedCustomerId = reservedByInput.getText();
            var checkoutBookEndpoint = this.endpointUrl + "/customers/" + linkedCustomerId + "/checkout";
            if (editing) {
                try {
                    if (!linkedCustomerId.equals(originalReservedById) && originalReservedById != null) {
                        var returnBookEndpoint = this.endpointUrl + "/customers/" + originalReservedById + "/return";
                        makePostRequest(returnBookEndpoint, Response.class, singletonList(bookId));
                    }
                    if (isBlank(linkedCustomerId) || linkedCustomerId.equals(originalReservedById)) {
                        return;
                    }
                    makePostRequest(checkoutBookEndpoint, Response.class, singletonList(Integer.parseInt(bookId)));
                } catch (Exception e) {
                    LOG.error("Unable to check if link exists");
                }
            } else {
                if (isBlank(linkedCustomerId)) {
                    return;
                }
                makePostRequest(checkoutBookEndpoint, Response.class, singletonList(bookId));
            }
        } catch (Exception e) {
            LOG.error("Something went wrong creating book customer link....");
        }
    }

    private void addDeleteButtonListener() {
        deleteButton.setOnMouseClicked(e -> {
            var book = (BookTableRow) Context.sharedData.get(EDIT_BOOK_KEY);
            var deleteBookEndpoint = this.endpointUrl + "/books/" + book.getId();
            try {
                makeDeleteRequest(deleteBookEndpoint, BookModel.class);
            } catch (Exception err) {
                LOG.error("Unable to delete the book " + err.getMessage());
            }
            Context.currentStage.close();
            Context.currentStage = null;
        });
    }

    private boolean findCustomer(String customerId) {
        var existsByIdEndpoint = this.endpointUrl + "/customer/" + customerId + "/exists";
        try {
            return makeGetRequest(existsByIdEndpoint, Boolean.class);
        } catch (Exception e) {
            LOG.error("Unable to see if customer exists..." + e.getMessage());
        }
        return false;
    }

    private void clearFields() {
        bookTitleInput.setText(EMPTY);
        bookAuthorInput.setText(EMPTY);
        yearInput.setText(EMPTY);
        isbnInput.setText(EMPTY);
    }
}
