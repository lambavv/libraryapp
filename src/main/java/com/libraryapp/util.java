package com.libraryapp;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class util {

    public static String REGEX_DIGITS_ONLY = "[0-9]+";
    public static String REGEX_ISBN_TEN_DIGITS = "ISBN[0-9]{10}";
    public static String REGEX_TEN_DIGITS = "[0-9]{10}";
    public static String findByLikePattern = "%%%s%%"; // to escape %, String.format uses %%
    public static DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static boolean match(String regex, String string) {
        var regexPattern = Pattern.compile(regex);
        return regexPattern.matcher(string).matches();
    }

    public static void enableButton(Button button) {
        button.setDisable(false);
        button.setOpacity(1);
    }

    public static void disableButton(Button button) {
        button.setDisable(true);
        button.setOpacity(0.66);
    }

    public static void showText(Text text) {
        text.setOpacity(1);
        text.setVisible(true);
    }

    public static void hideText(Text text) {
        text.setOpacity(0);
        text.setVisible(false);
    }

    public static void closeWindowFromEvent(ActionEvent actionEvent) {
        var source = (Node) actionEvent.getSource();
        var stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
