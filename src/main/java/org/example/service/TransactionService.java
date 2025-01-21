package org.example.service;

import org.example.model.Customer;
import org.example.model.Product;
import org.example.model.Transaction;
import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Customer customer, Product product, int quantity) {
        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setProduct(product);
        transaction.setQuantity(quantity);
        transaction.setDate(LocalDate.now());
        transaction.setTotalPrice(product.getPrice() * quantity);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByDateBetween(startDate, endDate);
    }
}
