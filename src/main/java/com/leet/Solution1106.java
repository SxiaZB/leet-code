package com.leet;

/**
 * @link: https://leetcode-cn.com/problems/parsing-a-boolean-expression/
 */
public class Solution1106 {
    public boolean parseBoolExpr(String expression) {
        return dfs(expression.toCharArray(), 0, '-') == 't';
    }

    private char dfs(char[] c, int i, char sign) {
        char cur = '-';
        while (i < c.length) {
            char nowC = c[i];
            if (nowC == '-' || nowC == ',' || nowC == '(') {
                i++;
                continue;
            }
            c[i] = '-';
            if (nowC == ')') {
                break;
            }
            if (nowC == '!' || nowC == '|' || nowC == '&') {
                nowC = dfs(c, i + 1, nowC);
            }
            if (cur == '-') {
                cur = nowC;
            }
            switch (sign) {
                case '!':
                    cur = !(nowC == 't') ? 't' : 'f';
                    break;
                case '|':
                    cur = ((cur == 't') || (nowC == 't')) ? 't' : 'f';
                    break;
                case '&':
                    cur = ((nowC == 't') && (cur == 't')) ? 't' : 'f';
                    break;
                default:
                    cur = nowC;
                    break;
            }
            i++;
        }
        return cur;
    }
}
