package com.leet;

/**
 * @link: https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
 */
public class Solution84 {
    public int largestRectangleArea(int[] heights) {
        int res = 0;
        for (int i = 0; i < heights.length; i++) {
            int height = heights[i];

            int start = i - 1;
            for (; start >= 0; start--) {
                if (heights[start] < height) {
                    break;
                }
            }
            int end = i + 1;
            for (; end < heights.length; end++) {
                if (heights[end] < height) {
                    break;
                }
            }
            int area = height * (end - start - 1);
            res = Math.max(res, area);
        }
        return res;
    }
}
