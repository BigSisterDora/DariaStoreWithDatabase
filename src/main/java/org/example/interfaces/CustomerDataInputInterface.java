package org.example.interfaces;

import org.example.model.Customer;

public interface CustomerDataInputInterface {
    Customer createCustomer();
    Customer editCustomer(Customer existingCustomer);
    void addCustomer();
    void listCustomers();
    void editCustomer();
    void deleteCustomer();
}