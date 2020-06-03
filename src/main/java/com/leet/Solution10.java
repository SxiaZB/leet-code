package com.leet;


/**
 * @link: https://leetcode-cn.com/problems/regular-expression-matching/
 */
public class Solution10 {

        /*
        func isMatch(s string, p string) bool {
            m, n := len(s), len(p)
            dp := make([][]bool, m + 1)
            for i := 0; i <= m; i++ {
                dp[i] = make([]bool, n + 1)
            }

            dp[m][n] = true

            // 只能倒着来，因为a*这种pattern，先得找到*，再找到a，才能正确
            for i := m; i >= 0; i-- { // 留意从m开始，因为字符串是""也有可能匹配
                for j := n - 1; j >= 0; j-- {
                    curMatched := i < m && (s[i] == p[j] || p[j] == '.')
                    if j < n - 1 && p[j + 1] == '*' {
                        dp[i][j] = dp[i][j + 2] || (curMatched && dp[i + 1][j])
                    } else {
                        dp[i][j] = curMatched && dp[i+1][j+1]
                    }
                }
            }

            return dp[0][0]
        }*/

    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        if (p.isEmpty()) {
            return s.isEmpty();
        }
        boolean first = s.length() > 0 && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.');
        if (p.length() >= 2 && p.charAt(1) == '*') {
            return (first && isMatch(s.substring(1), p)) || isMatch(s, p.substring(2));
        } else {
            return first && isMatch(s.substring(1), p.substring(1));
        }
    }
}
