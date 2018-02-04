package com.bbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Main class for the application.
 * 
 * @author Atanas Penev
 *
 */
@SpringBootApplication
@Import(ApplicationContext.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}