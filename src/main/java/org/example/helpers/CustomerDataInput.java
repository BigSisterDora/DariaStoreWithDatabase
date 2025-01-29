package org.example.helpers;

import org.example.model.Customer;
import org.example.tools.Input;

public class CustomerDataInput {
    public Customer createCustomer(Input input) {
        Customer customer = new Customer();
        System.out.println("===== New Customer =====");
        System.out.println("Enter customer name:");
        customer.setName(input.nextLine());
        System.out.println("Enter initial balance:");
        customer.setBalance(Double.parseDouble(input.nextLine()));
        return customer;
    }

    public Customer editCustomer(Customer existingCustomer, Input input) {
        System.out.println("===== Edit Customer =====");
        System.out.println("Current name: " + existingCustomer.getName());
        System.out.println("Enter new name (or press Enter to keep current):");
        String newName = input.nextLine();
        if (!newName.isEmpty()) {
            existingCustomer.setName(newName);
        }

        System.out.println("Current balance: " + existingCustomer.getBalance());
        System.out.println("Enter new balance (or press Enter to keep current):");
        String newBalance = input.nextLine();
        if (!newBalance.isEmpty()) {
            existingCustomer.setBalance(Double.parseDouble(newBalance));
        }
        return existingCustomer;
    }
}