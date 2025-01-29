package org.example.service;

import org.example.model.Customer;
import org.example.model.Product;
import org.example.model.Transaction;
import org.example.repository.CustomerRepository;
import org.example.repository.ProductRepository;
import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              CustomerRepository customerRepository,
                              ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Transaction createTransaction(Long customerId, Long productId, int quantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Покупатель с ID " + customerId + " не найден"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт с ID " + productId + " не найден"));

        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Недостаточно товара на складе. Доступно: " + product.getQuantity());
        }

        double totalPrice = product.getPrice() * quantity;
        if (customer.getBalance() < totalPrice) {
            throw new RuntimeException("У покупателя недостаточно средств. Требуется: " + totalPrice +
                    ", доступно: " + customer.getBalance());
        }

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setProduct(product);
        transaction.setQuantity(quantity);
        transaction.setTimestamp(LocalDateTime.now()); // Устанавливаем текущее время
        transaction.setTotalPrice(totalPrice);

        product.setQuantity(product.getQuantity() - quantity);
        customer.setBalance(customer.getBalance() - totalPrice);

        productRepository.save(product);
        customerRepository.save(customer);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        // Конвертируем LocalDate в LocalDateTime для начала и конца дня
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        return transactionRepository.findByTimestampBetween(startDateTime, endDateTime);
    }

    public double getRevenue() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().mapToDouble(Transaction::getTotalPrice).sum();
    }
}
