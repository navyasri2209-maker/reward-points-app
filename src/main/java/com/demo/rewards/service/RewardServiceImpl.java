package com.demo.rewards.service;

import com.demo.rewards.dto.RewardResponse;
import com.demo.rewards.exception.ResourceNotFoundException;
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

/**
 * Implementation of RewardService.
 * Contains business logic for calculating reward points.
 */
@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private TransactionRepository repository;

    private static final int MONTHS = 3;

    /**
     * Calculates reward points for all customers
     * based on transactions in the last 3 months.
     *
     * @return list of RewardResponse
     */
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
        for (Map.Entry<String, Map<String, Integer>> entry : customerMonthlyPoints.entrySet()) {
            String customerId = entry.getKey();
            Map<String, Integer> monthlyPoints = entry.getValue();
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

    /**
     * Calculates reward points for a specific customer.
     *
     * @param customerId customer identifier
     * @return RewardResponse
     */
    @Override
    public RewardResponse calculateRewardsByCustomer(String customerId) {
        List<RewardResponse> all = calculateRewards();
        return all.stream()
                .filter(r -> r.getCustomerId().equals(customerId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }
    }


