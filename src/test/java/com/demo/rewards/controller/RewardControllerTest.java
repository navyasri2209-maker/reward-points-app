package com.demo.rewards.controller;

import com.demo.rewards.dto.RewardResponse;
import com.demo.rewards.service.RewardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @Test
    void testGetRewards() throws Exception {

        RewardResponse response = new RewardResponse();
        response.setCustomerId("C1");
        response.setMonthlyPoints(Map.of("JANUARY", 100));
        response.setTotalPoints(100);
        Mockito.when(rewardService.calculateRewards())
                .thenReturn(List.of(response));
        mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value("C1"))
                .andExpect(jsonPath("$[0].totalPoints").value(100));
    }
}
