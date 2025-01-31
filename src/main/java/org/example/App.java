package org.example;

import org.example.helpers.ProductDataInput;
import org.example.helpers.CustomerDataInput;
import org.example.helpers.TransactionDataInput;
import org.example.model.Customer;
import org.example.model.Product;
import org.example.model.Transaction;
import org.example.service.ProductService;
import org.example.service.CustomerService;
import org.example.service.TransactionService;
import org.example.tools.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

    @Component
    public class App {
        private final Input input;
        private final ProductDataInput productDataInput;
        private final CustomerDataInput customerDataInput;
        private final TransactionDataInput transactionDataInput;

        @Autowired
        public App(Input input,
                   ProductDataInput productDataInput,
                   CustomerDataInput customerDataInput,
                   TransactionDataInput transactionDataInput) {
            this.input = input;
            this.productDataInput = productDataInput;
            this.customerDataInput = customerDataInput;
            this.transactionDataInput = transactionDataInput;
        }

        public void run() {
            boolean repeat = true;
            do {
                showMenu();
                try {
                    int choice = Integer.parseInt(input.nextLine());
                    switch (choice) {
                        case 0 -> repeat = false;
                        case 1 -> productDataInput.addProduct();
                        case 2 -> productDataInput.listProducts();
                        case 3 -> productDataInput.editProduct();
                        case 4 -> productDataInput.deleteProduct();
                        case 5 -> customerDataInput.addCustomer();
                        case 6 -> customerDataInput.listCustomers();
                        case 7 -> customerDataInput.editCustomer();
                        case 8 -> customerDataInput.deleteCustomer();
                        case 9 -> transactionDataInput.makePurchase();
                        default -> System.out.println("Invalid choice!");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } while (repeat);
        }

        private void showMenu() {
            System.out.println("\n===== Store Management System =====");
            System.out.println("1. Add Product");
            System.out.println("2. List Products");
            System.out.println("3. Edit Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Add Customer");
            System.out.println("6. List Customers");
            System.out.println("7. Edit Customer");
            System.out.println("8. Delete Customer");
            System.out.println("9. Make Purchase");
            System.out.println("0. Exit");
            System.out.print("Choose action: ");
        }

    }