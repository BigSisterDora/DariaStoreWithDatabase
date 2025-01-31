package org.example.helpers;

import org.example.model.Customer;
import org.example.model.Product;
import org.example.service.CustomerService;
import org.example.service.ProductService;
import org.example.service.TransactionService;
import org.example.tools.Input;
import org.springframework.stereotype.Component;

@Component
public class TransactionDataInput {
    private final Input input;
    private final TransactionService transactionService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CustomerDataInput customerDataInput;
    private final ProductDataInput productDataInput;

    public TransactionDataInput(Input input,
                                TransactionService transactionService,
                                CustomerService customerService,
                                ProductService productService,
                                CustomerDataInput customerDataInput,
                                ProductDataInput productDataInput) {
        this.input = input;
        this.transactionService = transactionService;
        this.customerService = customerService;
        this.productService = productService;
        this.customerDataInput = customerDataInput;
        this.productDataInput = productDataInput;
    }

    public void makePurchase() {
        System.out.println("\n===== New Purchase =====");
        customerDataInput.listCustomers();
        System.out.println("Enter customer ID:");
        Long customerId = Long.parseLong(input.nextLine());
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        productDataInput.listProducts();
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
            transactionService.createTransaction(customerId, productId, quantity);
            System.out.println("Purchase completed successfully!");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}