package org.example;

import org.example.controller.StoreController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreManagementApplication implements CommandLineRunner {
    private final StoreController storeController;

    public StoreManagementApplication(StoreController storeController) {
        this.storeController = storeController;
    }

    public static void main(String[] args) {
        SpringApplication.run(StoreManagementApplication.class, args);
    }

    @Override
    public void run(String... args) {
        storeController.start();
    }
}
