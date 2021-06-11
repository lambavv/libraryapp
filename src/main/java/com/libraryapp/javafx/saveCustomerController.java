package com.libraryapp.javafx;

import com.libraryapp.Context;
import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.domain.request.CustomerRequest;
import com.libraryapp.javafx.domain.CustomerTableRow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import static com.libraryapp.Context.EDIT_CUSTOMER_KEY;
import static com.libraryapp.util.*;


@Component
@FxmlView("saveCustomerWindow.fxml")
public class saveCustomerController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(saveCustomerController.class);

    @FXML
    private TextField fullNameInput;
    @FXML
    private DatePicker dobInput;
    @FXML
    private TextField addressInput;
    @FXML
    private Text ageWarning;

    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;

    private boolean editing;

    public void initialize() {
        editing = false;
        if (Context.sharedData.containsKey(EDIT_CUSTOMER_KEY)) {
            var customer = (CustomerTableRow) Context.sharedData.get(EDIT_CUSTOMER_KEY);
            updateFieldValues(customer);
            enableButton(saveButton);
            enableButton(deleteButton);
            editing = true;
        }
        addCancelButtonListener();
        addEmptyFieldSaveButtonValidation();
        addDeleteButtonListener();
        addSaveButtonListener();
    }

    private void addEmptyFieldSaveButtonValidation() {
        addListenerToInputToEnableSaveButton(fullNameInput);
        addListenerToInputToEnableSaveButton(addressInput);
        addListenerToInputToEnableSaveButton(dobInput);
    }

    private void addListenerToInputToEnableSaveButton(TextField input) {
        input.setOnKeyReleased(e -> {
            if (input.getText().isBlank()) {
                disableButton(saveButton);
            } else if (allFieldsHaveText()) {
                enableButton(saveButton);
            }
        });
    }

    private void addListenerToInputToEnableSaveButton(DatePicker date) {
        date.setOnKeyReleased(e -> {
            if (date.getValue() == null) {
                disableButton(saveButton);
            } else if (allFieldsHaveText()) {
                enableButton(saveButton);
            }
        });
    }

    private boolean allFieldsHaveText() {
        return !addressInput.getText().isBlank()
                && !fullNameInput.getText().isBlank()
                && dobInput.getValue() != null;
    }

    private void addCancelButtonListener() {
        cancelButton.setOnMouseClicked(e -> {
            Context.currentStage.close();
            Context.currentStage = null;
        });
    }

    private void updateFieldValues(CustomerTableRow customerTableRow) {
        fullNameInput.setText(customerTableRow.getFullName());
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(customerTableRow.getDateOfBirth(), DEFAULT_DATE_FORMATTER);
            dobInput.setValue(localDate);
        } catch (Exception e) {
            LOG.error("Unable to parse customer DB... invalid format. " + e.getMessage()
                    + "\nCustomer DOB: " + customerTableRow.getDateOfBirth());
        }
        addressInput.setText(customerTableRow.getAddress());
    }

    private void addDeleteButtonListener() {
        deleteButton.setOnMouseClicked(e -> {
            var customer = (CustomerTableRow) Context.sharedData.get(EDIT_CUSTOMER_KEY);
            var deleteBookEndpoint = this.endpointUrl + "/customers/" + customer.getId();
            try {
                makeDeleteRequest(deleteBookEndpoint, CustomerModel.class);
            } catch (Exception err) {
                LOG.error("Unable to delete the book " + err.getMessage());
            }
            Context.currentStage.close();
            Context.currentStage = null;
        });
    }
    private void addSaveButtonListener() {
        saveButton.setOnMouseClicked(e -> {
            saveCustomer();
            Context.currentStage.close();
            Context.currentStage = null;
        });
    }

    private void saveCustomer() {
        var customerRequest = new CustomerRequest()
                .setAddress(addressInput.getText())
                .setDateOfBirth(dobInput.getValue().toString())
                .setFullName(fullNameInput.getText());
        var endpointUrl = this.endpointUrl;
        if (editing) {
            var customer = (CustomerTableRow) Context.sharedData.get(EDIT_CUSTOMER_KEY);
            var customerId = customer.getId();
            endpointUrl = endpointUrl + "/customers/" + customerId;
            try {
                var resp = makePutRequest(endpointUrl, CustomerModel.class, customerRequest);
                LOG.info("Customer successfully updated. Id:" + resp.getId());
            } catch (Exception e) {
                LOG.error("Unable to update the customer..." + e.getMessage());
            }
        } else {
            endpointUrl = endpointUrl + "/customers";
            try {
                var resp = makePostRequest(endpointUrl, CustomerModel.class, customerRequest);
                var customerId = String.valueOf(resp.getId());
                LOG.info("New customer successfully created. Id:" + customerId);
            } catch (Exception e) {
                LOG.error("Unable to create the customer... | " + e.getMessage());
            }
        }
    }
}
