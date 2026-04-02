package com.demo.rewards.service;

import com.demo.rewards.dto.RewardResponse;

import java.util.List;

/**
 * Service interface for reward calculation.
 */
public interface RewardService {

    /**
     * Calculate rewards for all customers.
     *
     * @return list of RewardResponse
     */
    List<RewardResponse> calculateRewards();

    /**
     * Calculate rewards for a specific customer.
     *
     * @param customerId customer identifier
     * @return RewardResponse for the given customer
     */
    RewardResponse calculateRewardsByCustomer(String customerId);
}