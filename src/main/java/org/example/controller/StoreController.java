package org.example.controller;

import org.example.model.Customer;
import org.example.model.Product;
import org.example.service.CustomerService;
import org.example.service.ProductService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class StoreController {
    private final ProductService productService;
    private final CustomerService customerService;

    public StoreController(ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1 - Add Product");
            System.out.println("2 - List Products");
            System.out.println("3 - Edit Product");
            System.out.println("4 - Delete Product");
            System.out.println("5 - Add Customer");
            System.out.println("6 - List Customers");
            System.out.println("7 - Edit Customer");
            System.out.println("8 - Delete Customer");
            System.out.println("9 - Buy Product");
            System.out.println("0 - Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addProduct(scanner);
                case 2 -> listProducts();
                case 3 -> editProduct(scanner);
                case 4 -> deleteProduct(scanner);
                case 5 -> addCustomer(scanner);
                case 6 -> listCustomers();
                case 7 -> editCustomer(scanner);
                case 8 -> deleteCustomer(scanner);
                case 9 -> buyProduct(scanner);
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addProduct(Scanner scanner) {
        System.out.println("Enter product name:");
        String name = scanner.nextLine();
        System.out.println("Enter product price:");
        double price = scanner.nextDouble();
        System.out.println("Enter product quantity:");
        int quantity = scanner.nextInt();

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        productService.addProduct(product);
        System.out.println("Product added!");
    }

    private void listProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            System.out.println("Available Products:");
            for (Product product : products) {
                System.out.printf("ID: %d, Имя продукта: %s, Цена: %.2f, Количество: %d%n",
                        product.getId(), product.getName(), product.getPrice(), product.getQuantity());
            }
        }
    }

    private void editProduct(Scanner scanner) {
        System.out.println("Enter product ID to edit:");
        Long productId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Enter new product name (current: " + product.getName() + "):");
        String name = scanner.nextLine();
        System.out.println("Enter new product price (current: " + product.getPrice() + "):");
        double price = scanner.nextDouble();
        System.out.println("Enter new product quantity (current: " + product.getQuantity() + "):");
        int quantity = scanner.nextInt();

        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        productService.updateProduct(product);

        System.out.println("Product updated!");
    }

    private void deleteProduct(Scanner scanner) {
        System.out.println("Enter product ID to delete:");
        Long productId = scanner.nextLong();

        if (productService.deleteProductById(productId)) {
            System.out.println("Product deleted!");
        } else {
            System.out.println("Product not found.");
        }
    }

    private void addCustomer(Scanner scanner) {
        System.out.println("Enter customer name:");
        String name = scanner.nextLine();
        System.out.println("Enter customer balance:");
        double balance = scanner.nextDouble();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setBalance(balance);

        customerService.addCustomer(customer);
        System.out.println("Customer added!");
    }

    private void listCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers available.");
        } else {
            System.out.println("Customers List:");
            for (Customer customer : customers) {
                System.out.printf("ID: %d, Имя покупателя: %s, Баланс: %.2f%n",
                        customer.getId(), customer.getName(), customer.getBalance());
            }
        }
    }

    private void editCustomer(Scanner scanner) {
        System.out.println("Enter customer ID to edit:");
        Long customerId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("Enter new customer name (current: " + customer.getName() + "):");
        String name = scanner.nextLine();
        System.out.println("Enter new customer balance (current: " + customer.getBalance() + "):");
        double balance = scanner.nextDouble();

        customer.setName(name);
        customer.setBalance(balance);
        customerService.updateCustomer(customer);

        System.out.println("Customer updated!");
    }

    private void deleteCustomer(Scanner scanner) {
        System.out.println("Enter customer ID to delete:");
        Long customerId = scanner.nextLong();

        if (customerService.deleteCustomerById(customerId)) {
            System.out.println("Customer deleted!");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private void buyProduct(Scanner scanner) {
        System.out.println("Enter customer ID:");
        Long customerId = scanner.nextLong();
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("Enter product ID:");
        Long productId = scanner.nextLong();
        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Enter quantity to buy:");
        int quantity = scanner.nextInt();

        if (product.getQuantity() < quantity) {
            System.out.println("Not enough stock.");
            return;
        }

        double totalCost = product.getPrice() * quantity;
        if (customer.getBalance() < totalCost) {
            System.out.println("Not enough balance.");
            return;
        }

        // Update stock and balance
        product.setQuantity(product.getQuantity() - quantity);
        if (product.getQuantity() == 0) {
            productService.deleteProductById(product.getId());
        } else {
            productService.updateProduct(product);
        }

        customer.setBalance(customer.getBalance() - totalCost);
        customerService.updateCustomer(customer);

        System.out.println("Purchase successful!");
    }
}
