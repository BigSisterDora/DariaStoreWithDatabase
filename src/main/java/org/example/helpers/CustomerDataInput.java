package org.example.helpers;

import org.example.interfaces.CustomerDataInputInterface;
import org.example.model.Customer;
import org.example.service.CustomerService;
import org.example.tools.Input;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataInput implements CustomerDataInputInterface {
    private final Input input;
    private final CustomerService customerService;

    public CustomerDataInput(Input input, CustomerService customerService) {
        this.input = input;
        this.customerService = customerService;
    }

    @Override
    public Customer createCustomer() {
        Customer customer = new Customer();
        System.out.println("===== New Customer =====");
        System.out.println("Enter customer name:");
        customer.setName(input.nextLine());
        System.out.println("Enter initial balance:");
        customer.setBalance(Double.parseDouble(input.nextLine()));
        return customer;
    }

    @Override
    public Customer editCustomer(Customer existingCustomer) {
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

    @Override
    public void addCustomer() {
        Customer customer = createCustomer();
        customerService.addCustomer(customer);
    }

    @Override
    public void listCustomers() {
        System.out.println("\n===== Customer List =====");
        customerService.getAllCustomers().forEach(customer ->
                System.out.printf("ID: %d, Name: %s, Balance: %.2f%n",
                        customer.getId(), customer.getName(),
                        customer.getBalance())
        );
    }

    @Override
    public void editCustomer() {
        System.out.println("Enter customer ID to edit:");
        Long id = Long.parseLong(input.nextLine());
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            customer = editCustomer(customer);
            customerService.updateCustomer(customer);
        }
    }

    @Override
    public void deleteCustomer() {
        System.out.println("Enter customer ID to delete:");
        Long id = Long.parseLong(input.nextLine());
        customerService.deleteCustomerById(id);
    }
}