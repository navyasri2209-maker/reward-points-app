package com.demo.rewards.service;

import com.demo.rewards.dto.RewardResponse;
import com.demo.rewards.model.Transaction;
import com.demo.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RewardServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private RewardService rewardService;

    @Test
    void testCalculateRewards_withValidTransactions() {
        Transaction t1 = new Transaction();
        t1.setCustomerId("C1");
        t1.setAmount(120);
        t1.setDate(LocalDate.now().minusDays(10)); // within 3 months
        Transaction t2 = new Transaction();
        t2.setCustomerId("C1");
        t2.setAmount(75);
        t2.setDate(LocalDate.now().minusDays(20)); // within 3 months
        when(repository.findAll()).thenReturn(List.of(t1, t2));
        List<RewardResponse> result = rewardService.calculateRewards();
        assertNotNull(result);
        assertEquals(1, result.size());
        RewardResponse response = result.get(0);
        assertEquals("C1", response.getCustomerId());
        assertEquals(115, response.getTotalPoints());
    }

    @Test
    void testCalculateRewards_ignoreOldTransactions() {
        Transaction oldTxn = new Transaction();
        oldTxn.setCustomerId("C1");
        oldTxn.setAmount(200);
        oldTxn.setDate(LocalDate.now().minusMonths(4)); // OUTSIDE 3 months
        when(repository.findAll()).thenReturn(List.of(oldTxn));
        List<RewardResponse> result = rewardService.calculateRewards();
        assertTrue(result.isEmpty());
    }

    @Test
    void testCalculateRewards_multipleCustomers() {
        Transaction t1 = new Transaction();
        t1.setCustomerId("C1");
        t1.setAmount(120);
        t1.setDate(LocalDate.now().minusDays(5));
        Transaction t2 = new Transaction();
        t2.setCustomerId("C2");
        t2.setAmount(100);
        t2.setDate(LocalDate.now().minusDays(5));
        when(repository.findAll()).thenReturn(List.of(t1, t2));
        List<RewardResponse> result = rewardService.calculateRewards();
        assertEquals(2, result.size());
    }

    @Test
    void testCalculateRewards_emptyTransactions() {
        when(repository.findAll()).thenReturn(List.of());
        List<RewardResponse> result = rewardService.calculateRewards();
        assertTrue(result.isEmpty());
    }
}