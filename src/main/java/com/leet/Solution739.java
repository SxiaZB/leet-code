package com.leet;

import java.util.Stack;

/**
 * @link: https://leetcode-cn.com/problems/daily-temperatures/
 */
public class Solution739 {
    public int[] dailyTemperatures(int[] T) {
        int[] res = new int[T.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < T.length; i++) {
            while (stack.size() > 0 && T[stack.peek()] < T[i]) {
                Integer pop = stack.pop();
                res[pop] = i - pop;
            }
            stack.push(i);
        }
        return res;
    }
}
