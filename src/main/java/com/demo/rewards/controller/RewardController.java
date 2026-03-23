package com.demo.rewards.controller;

import com.demo.rewards.dto.RewardResponse;
import com.demo.rewards.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @GetMapping("/rewards")
    public ResponseEntity<List<RewardResponse>> getRewards() {
        List<RewardResponse> response = rewardService.calculateRewards();
        return ResponseEntity.ok(response);
    }
}