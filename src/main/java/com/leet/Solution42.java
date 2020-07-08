package com.leet;

import java.util.Stack;

public class Solution42 {
    public int trap(int[] height) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty()&&height[stack.peek()] < height[i]) {
                int hm = height[stack.pop()];
                if (stack.isEmpty()) {
                    break;
                }
                int w = i - stack.peek() - 1;
                int h = Math.min(height[i], height[stack.peek()]) - hm;
                res += w * h;
            }
            stack.push(i);
        }
        return res;
    }
}
