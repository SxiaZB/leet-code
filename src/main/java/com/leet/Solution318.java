package com.leet;


import java.util.Arrays;

public class Solution318 {
    public int maxProduct(String[] words) {
        int i = 0, j = 0;
        int length = words.length;
        Arrays.sort(words, (a, b) -> b.length() - a.length());
        for (; i < length; i++) {
            j = i + 1;
            for (; j < length; j++) {
                if (!isRepeat(words[i], words[j])) {
                    return words[i].length() * words[j].length();
                }
            }
        }
        return 0;
    }

    public boolean isRepeat(String i, String j) {
        int length = i.length();
        for (int k = 0; k < length; k++) {
            if (j.indexOf(i.codePointAt(k)) > -1) {
                return true;
            }
        }
        return false;
    }
}
