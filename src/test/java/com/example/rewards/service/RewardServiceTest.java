package com.example.rewards.service;

import com.example.rewards.dto.RewardResponse;
import com.example.rewards.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RewardServiceTest {

    @Autowired
    private RewardService rewardService;

    @Test
    void testRewardCalculation() {

        List<Transaction> transactions = List.of(
                new Transaction("C1", 120, LocalDate.now())
        );

        List<RewardResponse> response = rewardService.calculateRewards(transactions);

        assertEquals(90, response.get(0).getTotalPoints());
    }
}
