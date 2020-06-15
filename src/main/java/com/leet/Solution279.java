package com.leet;

import java.util.ArrayList;
import java.util.List;

public class Solution279 {
    public int numSquares(int n) {
        List<Integer> squares = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            squares.add(i * i);
        }
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int minStep = 0;
            for (int j = 0; j < squares.size(); j++) {
                Integer s = squares.get(j);
                if (s > i) {
                    break;
                }
                minStep = minStep == 0 ? dp[i - s] + 1 : Math.min(minStep, dp[i - s] + 1);
            }
            dp[i] = minStep;
        }
        return dp[n];
    }
}
