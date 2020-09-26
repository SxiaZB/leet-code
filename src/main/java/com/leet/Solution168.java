package com.leet;

/**
 * @link: https://leetcode-cn.com/problems/excel-sheet-column-title/
 */
public class Solution168 {
    public String convertToTitle(int n) {
        StringBuilder res = new StringBuilder();
        while (n != 0) {
            int mod = n % 26;
            if (mod == 0) {
                mod = 26;
                n--;
            }
            n = n / 26;
            res.append((char) ('A' + mod - 1));
        }
        return res.reverse().toString();
    }
}
