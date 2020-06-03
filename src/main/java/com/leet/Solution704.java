package com.leet;

public class Solution704 {
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int i = (r + l) / 2;
            if (nums[i] == target) {
                return i;
            } else if (nums[i] < target) {
                l = i + 1;
            } else {
                r = i - 1;
            }
        }
        return -1;
    }
}
