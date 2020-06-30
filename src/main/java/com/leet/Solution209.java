package com.leet;

public class Solution209 {
    public int minSubArrayLen(int s, int[] nums) {
        int res = 0, sum = 0, l = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= s) {
                return 1;
            }
            sum += nums[i];
            while ((sum - nums[l]) >= s) {
                sum -= nums[l];
                l++;
            }
            if (sum >= s) {
                int size = i - l + 1;
                res = res == 0 ? size : Math.min(size, res);
            }
        }
        return res;
    }
}
