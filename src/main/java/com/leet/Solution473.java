package com.leet;

import java.util.Arrays;

public class Solution473 {
    private int[] sums = new int[4];
    private int side;

    public boolean makesquare(int[] nums) {
        if (nums.length < 4) {
            return false;
        }
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum = sum + nums[i];
        }
        if (sum % 4 != 0) {
            return false;
        }
        side = sum / 4;
        if (nums[nums.length - 1] > side) {
            return false;
        }
        return recursive(nums.length - 1, nums);
    }

    private boolean recursive(int now, int[] nums) {
        if (now < 0) {
            return sums[0] == side && sums[1] == side && sums[2] == side;
        }
        for (int i = 0; i < 4; i++) {
            if (sums[i] + nums[now] <= side) {
                sums[i] = sums[i] + nums[now];
                if (recursive(now - 1, nums)) {
                    return true;
                }
                sums[i] = sums[i] - nums[now];
            }
        }
        return false;
    }
}
