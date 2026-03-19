package com.example.rewards.service;

import com.example.rewards.dto.RewardResponse;
import com.example.rewards.model.Transaction;
import com.example.rewards.util.RewardUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardService {
    public List<RewardResponse> calculateRewards(List<Transaction> transactions) {

        Map<String, Map<String, Integer>> customerMonthlyPoints = new HashMap<>();

        for (Transaction txn : transactions) {

            int points = RewardUtil.calculatePoints(txn.getAmount());

            String month = txn.getDate().getMonth().toString();
            String customerId = txn.getCustomerId();

            customerMonthlyPoints
                    .computeIfAbsent(customerId, k -> new HashMap<>())
                    .merge(month, points, Integer::sum);
        }

        List<RewardResponse> responses = new ArrayList<>();

        for (String customer : customerMonthlyPoints.keySet()) {

            Map<String, Integer> monthly = customerMonthlyPoints.get(customer);
            int total = monthly.values().stream().mapToInt(Integer::intValue).sum();

            RewardResponse res = new RewardResponse();
            res.setCustomerId(customer);
            res.setMonthlyPoints(monthly);
            res.setTotalPoints(total);

            responses.add(res);
        }

        return responses;
    }
}
