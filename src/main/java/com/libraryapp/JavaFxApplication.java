package com.libraryapp;

import com.libraryapp.javafx.loginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        var args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(LibraryAppApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
        var fxWeaver = applicationContext.getBean(FxWeaver.class);
        var root = (Parent) fxWeaver.loadView(loginController.class);
        var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Context.fxWeaver = fxWeaver;
        Context.mainScene = scene;
        Context.mainStage = stage;
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
