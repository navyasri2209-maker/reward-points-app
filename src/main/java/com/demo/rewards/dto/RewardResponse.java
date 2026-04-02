package com.demo.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO representing reward response for a customer.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RewardResponse {

    /** Customer identifier */
    private String customerId;

    /** Monthly reward points */
    private Map<String, Integer> monthlyPoints;

    /** Total reward points */
    private int totalPoints;
}
