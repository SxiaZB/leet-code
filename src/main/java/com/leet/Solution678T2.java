package com.leet;

public class Solution678T2 {
    public boolean checkValidString(String s) {
        int min = 0, max = 0;
        for (int i = 0; i < s.length(); i++) {
            char nc = s.charAt(i);
            if (nc == '(') {
                min++;
                max++;
            } else if (nc == ')') {
                if (max < 1) {
                    return false;
                }
                max--;
                if (min > 0) {
                    min--;
                }
            } else {
                max++;
                if (min > 0) {
                    min--;
                }
            }
        }
        return min == 0;
    }
}
