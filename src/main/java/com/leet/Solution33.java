package com.leet;

public class Solution33 {
    public int search(int[] nums, int target) {
        return recursive(nums, target, 0, nums.length - 1);
    }

    public int recursive(int[] nums, int target, int l, int r) {
        if (l > r || (nums[l] < nums[r] && (nums[l] > target || nums[r] < target))) {
            return -1;
        }
        if (nums[l] < nums[r]) {
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
        } else {
            int i = (r + l) / 2;
            if (nums[i] == target) {
                return i;
            } else {
                return Math.max(recursive(nums, target, l, i - 1), recursive(nums, target, i + 1, r));
            }
        }
    }
}
