package com.libraryapp.javafx;


import com.libraryapp.Context;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@FxmlView("loginWindow.fxml")
public class loginController {

    @Autowired
    private Environment environment;

    @FXML
    private Slider hintSlider;
    @FXML
    private Text textHint1;
    @FXML
    private Text textHint2;
    @FXML
    private Text textHint3;
    @FXML
    private Text textHint4;
    @FXML
    private Text textHint5;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Text errorMessage;


    public void initialize() {
        addEnterKeyPressToPasswordField();
        addSliderHintListener();
        addLoginButtonListener();
    }

    private void addEnterKeyPressToPasswordField() {
        passwordField.setOnKeyPressed(e -> {
            errorMessage.setVisible(false);
        });
        passwordField.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });
    }

    private void addSliderHintListener() {
        hintSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            var newSliderValue = newValue.doubleValue()/100;
            textHint1.setOpacity(1 - newSliderValue);
            textHint2.setOpacity(1 - newSliderValue*1.25);
            textHint3.setOpacity(1 - newSliderValue*1.75);
            textHint4.setOpacity(1 - newSliderValue*2.5);
            textHint5.setOpacity(1 - newSliderValue*5);
        });
    }

    private void addLoginButtonListener() {
        loginButton.setOnAction(e -> handleLoginAttempt());
    }

    private void handleLoginAttempt() {
        var securePassword = environment.getProperty("security.password");
        var userInput = passwordField.getText();
        if(securePassword.equals(userInput)) {
            var stage = Context.mainStage;
            var root = (Parent) Context.fxWeaver.loadView(mainController.class);
            var scene = new Scene(root);
            Context.mainScene = scene;
            stage.setScene(scene);
            stage.show();
        } else {
            errorMessage.setVisible(true);
        }
    }
}
