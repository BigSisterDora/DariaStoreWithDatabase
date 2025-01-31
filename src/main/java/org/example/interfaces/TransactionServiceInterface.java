package org.example.interfaces;

import org.example.model.Transaction;
import java.time.LocalDate;
import java.util.List;

public interface TransactionServiceInterface {
    Transaction createTransaction(Long customerId, Long productId, int quantity);
    List<Transaction> getTransactionsByDate(LocalDate startDate, LocalDate endDate);
    double getRevenue();
}