package com.leet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Solution826 {

    class Job {
        private int difficulty;
        private int profit;

        public Job(int difficulty, int profit) {
            this.difficulty = difficulty;
            this.profit = profit;
        }
    }

    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        List<Job> jobs = new ArrayList<>(difficulty.length);
        for (int i = 0; i < difficulty.length; i++) {
            jobs.add(new Job(difficulty[i], profit[i]));
        }
        jobs.sort(Comparator.comparingInt(a -> a.difficulty));
        Arrays.sort(worker);
        int res = 0;
        int j = 0;
        int minProfit = 0;
        for (int i = 0; i < worker.length; i++) {
            int work = worker[i];
            for (; jobs.size() > j && work >= jobs.get(j).difficulty; j++) {
                if (jobs.get(j).profit > minProfit) {
                    minProfit = jobs.get(j).profit;
                }
            }
            res = res + minProfit;
        }

        return res;
    }

    private int getMaxProfit(int[] difficulty, int[] profit, int work) {
        int res = 0;
        for (int i = 0; i < difficulty.length; i++) {
            if (difficulty[i] <= work && profit[i] > res) {
                res = profit[i];
            }
        }
        return res;
    }
}
