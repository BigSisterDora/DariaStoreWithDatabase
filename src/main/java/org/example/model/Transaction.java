package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime; // Изменим импорт

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

    private int quantity;

    @Column(name = "timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp; // Изменим тип и имя поля

    private Double totalPrice;
}