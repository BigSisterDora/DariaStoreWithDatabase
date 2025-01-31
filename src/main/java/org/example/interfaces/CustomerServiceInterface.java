package org.example.interfaces;

import org.example.model.Customer;
import java.util.List;

public interface CustomerServiceInterface {
    Customer addCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer updateCustomer(Customer customer);
    boolean deleteCustomerById(Long id);
}