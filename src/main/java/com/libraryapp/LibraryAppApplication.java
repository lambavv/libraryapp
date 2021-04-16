package com.libraryapp;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

import com.libraryapp.services.terminal.TerminalService;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.LibraryApp")
@EntityScan("com.LibraryApp.domain")
public class LibraryAppApplication {

    @Inject
    TerminalService terminalService;

    private static final Logger LOG = LoggerFactory.getLogger(LibraryAppApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LibraryAppApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            LOG.info("Starting LibraryApp...");
            LOG.info("Loading interface...");
            terminalService.openTerminal();
        };
    }
}
