package com.example.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String customerId;
    private double amount;
    private LocalDate date;
}
