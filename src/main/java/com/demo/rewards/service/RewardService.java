package com.demo.rewards.service;

import com.demo.rewards.dto.RewardResponse;
import com.demo.rewards.model.Transaction;
import com.demo.rewards.repository.TransactionRepository;
import com.demo.rewards.util.RewardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardService {

    @Autowired
    private TransactionRepository repository;

    private static final int MONTHS = 3;

    public List<RewardResponse> calculateRewards() {
        List<Transaction> transactions = repository.findAll();
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(MONTHS);
        Map<String, Map<String, Integer>> customerMonthlyPoints = new HashMap<>();
        for (Transaction txn : transactions) {
            if (txn.getDate().isBefore(threeMonthsAgo)) continue;
            int points = RewardUtil.calculatePoints(txn.getAmount());
            String month = txn.getDate().getMonth().toString();
            String customerId = txn.getCustomerId();
            customerMonthlyPoints
                    .computeIfAbsent(customerId, k -> new HashMap<>())
                    .merge(month, points, Integer::sum);
        }
        return mapToResponse(customerMonthlyPoints);
    }

    private List<RewardResponse> mapToResponse(Map<String, Map<String, Integer>> customerMonthlyPoints) {
        List<RewardResponse> responses = new ArrayList<>();
        for (String customerId : customerMonthlyPoints.keySet()) {
            Map<String, Integer> monthlyPoints = customerMonthlyPoints.get(customerId);
            int totalPoints = monthlyPoints.values()
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum();
            RewardResponse response = new RewardResponse();
            response.setCustomerId(customerId);
            response.setMonthlyPoints(monthlyPoints);
            response.setTotalPoints(totalPoints);
            responses.add(response);
        }
        return responses;
    }
}