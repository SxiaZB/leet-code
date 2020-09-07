package com.leet;

import java.util.HashMap;

/**
 * @link：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 */
public class Solution3 {
    public int lengthOfLongestSubstring(String s) {
        int res = 0;
        int l = 0;
        int r = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char a = s.charAt(i);
            if (map.containsKey(a)) {
                l = Math.max(l, map.get(a));
            }
            map.put(a, i + 1);
            res = Math.max(res, r - l + 1);
            r++;
        }
        return res;
    }
}
