package com.leet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution139 {
    private Map<String, Boolean> map = new HashMap<>();

    public boolean wordBreak(String s, List<String> wordDict) {
        int length = s.length();
        boolean[] b = new boolean[length + 1];
        b[0] = true;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j <= length; j++) {
                if (b[i] && wordDict.contains(s.substring(i, j))) {
                    b[j] = true;
                }
            }
        }
        return b[length];
    }
}
