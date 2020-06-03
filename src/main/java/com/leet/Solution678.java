package com.leet;

/**
 * @link: https://leetcode-cn.com/problems/valid-parenthesis-string/
 */
public class Solution678 {
    public boolean checkValidString(String s) {
        return dfs(s, 0, 0, 0);
    }

    public boolean dfs(String s, int i, int l, int r) {
        for (; i < s.length(); i++) {
            char nc = s.charAt(i);
            if (nc == '(') {
                l++;
            } else if (nc == ')') {
                r++;
            } else {
                return dfs(s, i + 1, l, r) || dfs(s, i + 1, l + 1, r) || dfs(s, i + 1, l, r + 1);
            }
            if (r > l) {
                return false;
            }
        }
        return l == r;
    }
}
