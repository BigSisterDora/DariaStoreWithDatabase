package org.example;

import org.example.interfaces.*;
import org.example.tools.Input;
import org.springframework.stereotype.Component;

@Component
public class App {
    private final Input input;
    private final ProductDataInputInterface productDataInput;
    private final CustomerDataInputInterface customerDataInput;
    private final TransactionDataInputInterface transactionDataInput;

    public App(Input input,
               ProductDataInputInterface productDataInput,
               CustomerDataInputInterface customerDataInput,
               TransactionDataInputInterface transactionDataInput) {
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