package com.leet;

import java.util.HashMap;
import java.util.Map;

/**
 * @link: https://leetcode-cn.com/problems/implement-strstr/
 */
public class Solution28 {
    public int strStr(String haystack, String needle) {
        if (needle == null || "".equals(needle)) {
            return 0;
        }
        int m = needle.length();
        int n = haystack.length();
        Map<Character, Integer> bmBc = new HashMap<>();
        int[] bmGs = new int[m];
        preBmBc(needle, m, bmBc);
        preBmGs(needle, m, bmGs);
        int i = 0;
        int j = 0;
        while (j <= n - m) {
            for (i = m - 1; i >= 0; i--) {
                if (needle.charAt(i) != haystack.charAt(i + j)) {
                    break;
                }
            }
            if (i < 0) {
                return j;
            } else {
                j += Math.max(bmGs[i], getBmBc(haystack.charAt(i + j), bmBc, m) - (m - 1 - i));
            }
        }
        return -1;
    }

    private void preBmBc(String pattern, int patLength, Map<Character, Integer> bmBc) {
        for (int i = patLength - 2; i >= 0; i--) {
            if (!bmBc.containsKey(pattern.charAt(i))) {
                // 存入从右往左某个字符第一次出现距离尾部的距离
                bmBc.put(pattern.charAt(i), patLength - 1 - i);
            }
        }
    }

    private int getBmBc(char c, Map<Character, Integer> bmBc, int m) {
        //取坏字符算法得到的位移，存在则返回位移
        if (bmBc.containsKey(c)) {
            return bmBc.get(c);
        } else {
            return m;
        }
    }

    private void suffix(String pattern, int patLength, int[] suffix) {
        //suffix[i] = s 表示以i为边界，与模式串后缀匹配的最大长度
        suffix[patLength - 1] = patLength;
        int q = 0;
        for (int i = patLength - 2; i >= 0; i--) {
            q = i;
            while (q >= 0 && pattern.charAt(q) == pattern.charAt(patLength - 1 - (i - q))) {
                q--;
            }
            suffix[i] = i - q;
        }
    }

    private void preBmGs(String pattern, int patLength, int[] bmGs) {
        int i, j;
        int[] suffix = new int[patLength];
        suffix(pattern, patLength, suffix);
        //模式串中没有子串匹配上好后缀，也找不到一个最大前缀
        for (i = 0; i < patLength; i++) {
            bmGs[i] = patLength;
        }
        //模式串中没有子串匹配上好后缀，但找到一个最大前缀
        j = 0;
        for (i = patLength - 1; i >= 0; i--) {
            if (suffix[i] == i + 1) {
                for (; j < patLength - 1 - i; j++) {
                    if (bmGs[j] == patLength) {
                        bmGs[j] = patLength - 1 - i;
                    }
                }
            }
        }
        //模式串中有子串匹配上好后缀
        for (i = 0; i < patLength - 1; i++) {
            bmGs[patLength - 1 - suffix[i]] = patLength - 1 - i;
        }
    }
}
