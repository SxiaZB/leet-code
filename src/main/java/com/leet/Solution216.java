package com.leet;

import java.util.ArrayList;
import java.util.List;

/**
 * @link: https://leetcode-cn.com/problems/combination-sum-iii/
 */
public class Solution216 {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 1; i <= 9 && i <= 9 + 1 - k; i++) {
            List<Integer> now = new ArrayList<>(k);
            now.add(0, i);
            recursive(res, k, n, now, 0, i, i);
        }
        return res;
    }

    private void recursive(List<List<Integer>> res, int k, int n, List<Integer> now, int index, int pre, int nowSum) {
        if (nowSum > n || now.size() > k) {
            return;
        }
        if (now.size() == k) {
            if (nowSum == n) {
                res.add(new ArrayList<>(now));
            }
            return;
        }
        for (int i = pre + 1; i <= 9; i++) {
            now.add(index + 1, i);
            recursive(res, k, n, now, index + 1, i, nowSum + i);
            now.remove(index + 1);
        }
    }
}
