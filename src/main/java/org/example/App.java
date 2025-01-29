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
    private final ProductService productService;
    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final ProductDataInput productDataInput;
    private final CustomerDataInput customerDataInput;
    private final TransactionDataInput transactionDataInput;

    @Autowired
    public App(Input input,
               ProductService productService,
               CustomerService customerService,
               TransactionService transactionService) {
        this.input = input;
        this.productService = productService;
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.productDataInput = new ProductDataInput();
        this.customerDataInput = new CustomerDataInput();
        this.transactionDataInput = new TransactionDataInput();
    }

    public void run() {
        boolean repeat = true;
        do {
            showMenu();
            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 0 -> repeat = false;
                    case 1 -> addProduct();
                    case 2 -> listProducts();
                    case 3 -> editProduct();
                    case 4 -> deleteProduct();
                    case 5 -> addCustomer();
                    case 6 -> listCustomers();
                    case 7 -> editCustomer();
                    case 8 -> deleteCustomer();
                    case 9 -> makePurchase();
                    default -> System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (repeat);
        System.out.println("Thank you for using Store Management System!");
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

    private void addProduct() {
        Product product = productDataInput.createProduct(input);
        productService.addProduct(product);
    }

    private void listProducts() {
        System.out.println("\n===== Product List =====");
        productService.getAllProducts().forEach(product ->
                System.out.printf("ID: %d, Name: %s, Price: %.2f, Quantity: %d%n",
                        product.getId(), product.getName(),
                        product.getPrice(), product.getQuantity())
        );
    }

    private void editProduct() {
        System.out.println("Enter product ID to edit:");
        Long id = Long.parseLong(input.nextLine());
        productService.updateProduct(id);
    }

    private void deleteProduct() {
        System.out.println("Enter product ID to delete:");
        Long id = Long.parseLong(input.nextLine());
        productService.deleteProduct(id);
    }

    private void addCustomer() {
        Customer customer = customerDataInput.createCustomer(input);
        customerService.addCustomer(customer);
    }

    private void listCustomers() {
        System.out.println("\n===== Customer List =====");
        customerService.getAllCustomers().forEach(customer ->
                System.out.printf("ID: %d, Name: %s, Balance: %.2f%n",
                        customer.getId(), customer.getName(),
                        customer.getBalance())
        );
    }

    private void editCustomer() {
        System.out.println("Enter customer ID to edit:");
        Long id = Long.parseLong(input.nextLine());
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            customer = customerDataInput.editCustomer(customer, input);
            customerService.updateCustomer(customer);
        }
    }

    private void deleteCustomer() {
        System.out.println("Enter customer ID to delete:");
        Long id = Long.parseLong(input.nextLine());
        customerService.deleteCustomerById(id);
    }

    private void makePurchase() {
        System.out.println("\n===== New Purchase =====");

        listCustomers();
        System.out.println("Enter customer ID:");
        Long customerId = Long.parseLong(input.nextLine());
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        listProducts();
        System.out.println("Enter product ID:");
        Long productId = Long.parseLong(input.nextLine());
        Product product = productService.findById(productId);

        System.out.println("Enter quantity to purchase:");
        int quantity = Integer.parseInt(input.nextLine());

        if (product.getQuantity() < quantity) {
            System.out.println("Not enough stock!");
            return;
        }

        double totalPrice = product.getPrice() * quantity;
        if (customer.getBalance() < totalPrice) {
            System.out.println("Insufficient balance!");
            return;
        }

        try {
            // Create transaction using IDs instead of entities
            transactionService.createTransaction(customerId, productId, quantity);
            System.out.println("Purchase completed successfully!");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}