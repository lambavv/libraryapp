package com.libraryapp;

import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;

import java.util.HashMap;
import java.util.Map;

public class Context {

    public final static String EDIT_BOOK_KEY = "selectedBook";
    public final static String EDIT_CUSTOMER_KEY = "selectedCustomer";

    public static Stage mainStage;
    public static Scene mainScene;

    public static Stage currentStage;

    public static FxWeaver fxWeaver;
    public static Map<String, Object> sharedData = new HashMap<>();
}
