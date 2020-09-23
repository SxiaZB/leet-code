package com.leet;

/**
 * @link: https://leetcode-cn.com/problems/excel-sheet-column-number/
 */
public class Solution171 {
    public int titleToNumber(String s) {
        int res = 0;
        if (s != null) {
            // 从低到高
//            int length = s.length();
//            for (int i = length - 1; i >= 0; i--) {
//                res += Math.pow(26, (length - i -1)) * (s.charAt(i) - 'A' + 1);
//            }
            int length = s.length();
            for (int i = 0; i < length; i++) {
                res = 26 * res + (s.charAt(i) - 'A' + 1);
            }
        }
        return res;
    }
}
