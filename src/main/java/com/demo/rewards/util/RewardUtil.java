package com.demo.rewards.util;

/**
 * Utility class for reward calculation logic.
 */
public class RewardUtil {

    /**
     * Calculates reward points based on transaction amount.
     *
     * @param amount transaction amount
     * @return calculated reward points
     */
    public static int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (amount - 100) * 2;
            points += 50;
        } else if (amount > 50) {
            points += (amount - 50);
        }
        return points;
    }
}