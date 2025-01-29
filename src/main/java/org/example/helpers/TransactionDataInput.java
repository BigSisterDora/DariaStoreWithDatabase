package org.example.helpers;

import org.example.model.Customer;
import org.example.model.Product;
import org.example.model.Transaction;
import org.example.tools.Input;
import java.time.LocalDateTime;

public class TransactionDataInput {
    public Transaction createTransaction(Customer customer, Product product, Input input) {
        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setProduct(product);

        System.out.println("Enter quantity to purchase:");
        int quantity = Integer.parseInt(input.nextLine());
        transaction.setQuantity(quantity);

        double totalPrice = product.getPrice() * quantity;
        transaction.setTotalPrice(totalPrice);
        transaction.setTimestamp(LocalDateTime.now());

        return transaction;
    }
}