package com.demo.rewards.controller;

import com.demo.rewards.dto.RewardResponse;
import com.demo.rewards.service.RewardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * REST controller for handling reward-related APIs.
 * Provides endpoints to fetch rewards for all customers
 * and individual customers.
 */
@RestController
@RequestMapping("/api")
public class RewardController {

    @Autowired
    private RewardServiceImpl rewardServiceImpl;

    /**
     * Fetch reward points for all customers.
     *
     * @return list of RewardResponse containing customer reward details
     */
    @GetMapping("/rewards")
    public ResponseEntity<List<RewardResponse>> getRewards() {
        List<RewardResponse> response = rewardServiceImpl.calculateRewards();
        return ResponseEntity.ok(response);
    }

    /**
     * Fetch reward points for a specific customer.
     *
     * @param customerId unique identifier of customer
     * @return RewardResponse containing reward details
     */
    @GetMapping("/rewards/{customerId}")
    public ResponseEntity<RewardResponse> getRewardsByCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok(rewardServiceImpl.calculateRewardsByCustomer(customerId));
    }
}