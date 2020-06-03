package com.leet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution15 {
    public List<List<Integer>> threeSum(int[] nums) {
        int compare = 0;
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return res;
        }
        Arrays.sort(nums);
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            //如果当前数字大于需要相等的值，则结束循环
            if (nums[i] > compare) break;
            // 如果当前数字和上一个数字相同，则跳过该次循环
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int l = i + 1;
            int r = length - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == compare) {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    while (l < r && nums[l] == nums[l + 1]) {
                        // 去重
                        l++;
                    }
                    while (l < r && nums[r] == nums[r - 1]) {
                        // 去重
                        r--;
                    }
                    l++;
                    r--;
                } else if (sum > compare) {
                    r--;
                } else {
                    l++;
                }
            }
        }
        return res;
    }
}
