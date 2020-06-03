package com.leet;


/**
 * @link: https://leetcode-cn.com/problems/decode-string/
 */
public class Solution394 {
    public String decodeString(String s) {
        return dfs(s.toCharArray(), 0);
    }

    private String dfs(char[] c, int i) {
        StringBuilder cur = new StringBuilder();
        int swell = 0;
        while (i < c.length) {
            char nowC = c[i];
            if (nowC == '-') {
                i++;
                continue;
            }
            c[i] = '-';
            if (nowC >= '0' && nowC <= '9') {
                swell = swell * 10 + (nowC - '0');
            } else if (nowC == '[') {
                String dfs = dfs(c, i + 1);
                while (swell > 0) {
                    cur.append(dfs);
                    swell--;
                }
            } else if (nowC == ']') {
                break;
            } else {
                cur.append(nowC);
            }
            i++;
        }
        return cur.toString();
    }
}
