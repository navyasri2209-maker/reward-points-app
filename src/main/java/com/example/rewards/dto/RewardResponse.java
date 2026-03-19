package com.example.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RewardResponse {
    private String customerId;
    private Map<String, Integer> monthlyPoints;
    private int totalPoints;
}
