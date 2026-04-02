package com.demo.rewards;

import com.demo.rewards.controller.RewardController;
import com.demo.rewards.dto.RewardResponse;
import com.demo.rewards.exception.ResourceNotFoundException;
import com.demo.rewards.service.RewardService;
import com.demo.rewards.service.RewardServiceImpl;
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

// Load only controller layer
@WebMvcTest(RewardController.class)
class RewardIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock service layer
    @MockBean
    private RewardServiceImpl rewardServiceImpl;

    // Test: Get all rewards
    @Test
    void testGetRewards() throws Exception {

        RewardResponse response = new RewardResponse();
        response.setCustomerId("C1");
        response.setMonthlyPoints(Map.of("JANUARY", 100));
        response.setTotalPoints(100);

        Mockito.when(rewardServiceImpl.calculateRewards())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value("C1"))
                .andExpect(jsonPath("$[0].totalPoints").value(100));
    }

    // Test: Get reward by customer
    @Test
    void testGetRewardsByCustomer() throws Exception {

        RewardResponse response = new RewardResponse();
        response.setCustomerId("C1");
        response.setMonthlyPoints(Map.of("JANUARY", 100));
        response.setTotalPoints(100);

        Mockito.when(rewardServiceImpl.calculateRewardsByCustomer("C1"))
                .thenReturn(response);

        mockMvc.perform(get("/api/rewards/C1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("C1"))
                .andExpect(jsonPath("$.totalPoints").value(100));
    }

    // Test: Customer not found (404)
    @Test
    void testCustomerNotFound() throws Exception {

        Mockito.when(rewardServiceImpl.calculateRewardsByCustomer(Mockito.anyString()))
                .thenThrow(new ResourceNotFoundException("Customer not found"));

        mockMvc.perform(get("/api/rewards/99999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));
    }
}