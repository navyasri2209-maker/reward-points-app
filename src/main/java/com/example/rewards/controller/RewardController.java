package com.example.rewards.controller;

import com.example.rewards.dto.RewardResponse;
import com.example.rewards.model.Transaction;
import com.example.rewards.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @GetMapping
    public List<RewardResponse> getRewards() {

        List<Transaction> transactions = mockTransactions();

        return rewardService.calculateRewards(transactions);
    }

    private List<Transaction> mockTransactions() {

        return List.of(
                new Transaction("C1", 120, LocalDate.of(2026, 1, 10)),
                new Transaction("C1", 75, LocalDate.of(2026, 1, 15)),
                new Transaction("C1", 200, LocalDate.of(2026, 2, 10)),
                new Transaction("C2", 90, LocalDate.of(2026, 1, 5)),
                new Transaction("C2", 130, LocalDate.of(2026, 3, 20))
        );
    }
}