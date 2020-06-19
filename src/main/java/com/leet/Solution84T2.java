package com.leet;

import java.util.Stack;

public class Solution84T2 {
    public int largestRectangleArea(int[] heights) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i <= heights.length; i++) {
            while (!stack.isEmpty()) {
                if (i == heights.length || heights[stack.peek()] > heights[i]) {
                    int height = heights[stack.pop()];
                    int start = stack.isEmpty() ? -1 : stack.peek();
                    res = Math.max(res, height * (i - start - 1));
                } else {
                    break;
                }
            }
            stack.push(i);
        }
        return res;
    }
}
