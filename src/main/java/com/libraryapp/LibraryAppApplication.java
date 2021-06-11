package com.libraryapp;

import javax.inject.Inject;

import com.libraryapp.javafx.loginController;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import com.libraryapp.services.terminal.TerminalService;

@SpringBootApplication
@EnableEncryptableProperties
@EnableAutoConfiguration
@ComponentScan("com.libraryapp")
@ComponentScan(basePackageClasses={loginController.class})
@EntityScan("com.libraryapp.domain")
public class LibraryAppApplication {

    @Inject
    TerminalService terminalService;

    private static final Logger LOG = LoggerFactory.getLogger(LibraryAppApplication.class);

    public static void main(String[] args) {
        //SpringApplication.run(LibraryAppApplication.class, args);
        Application.launch(JavaFxApplication.class, args);
    }

}
