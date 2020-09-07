package com.leet;

public class Solution276 {

    //有 k 种颜色的涂料和一个包含 n 个栅栏柱的栅栏，每个栅栏柱可以用其中一种颜色进行上色。
//
//    你需要给所有栅栏柱上色，并且保证其中相邻的栅栏柱 最多连续两个 颜色相同。然后，返回所有有效涂色的方案数。
//
//    注意:
//    n 和 k 均为非负的整数。
//
//    示例:
//            ```
//    输入: n = 3，k = 2
//    输出: 6
//    解析: 用 c1 表示颜色 1，c2 表示颜色 2，所有可能的涂色方案有:
//
//                        柱 1    柱 2   柱 3
//            -----      -----  -----  -----
//            1         c1     c1     c2
//            2         c1     c2     c1
//            3         c1     c2     c2
//            4         c2     c1     c1 
//            5         c2     c1     c2
//            6         c2     c2     c1
    public int numWays(int n, int k) {
        if (n == 0) {
            return 0;
        }
        int[] dp1 = new int[n]; //与上一个不同
        int[] dp2 = new int[n]; //与上一个相同
        dp1[0] = k;
        for (int i = 1; i < n; i++) {
            dp1[i] = (dp1[i - 1] + dp2[i - 1]) * (k - 1);
            dp2[i] = dp1[i - 1];
        }
        return dp1[n - 1] + dp2[n - 1];
    }

    public int numWays2(int n, int k) {
        int[] dp = new int[n + 3];
        dp[0] = 0;
        dp[1] = k;
        dp[2] = k * k;
        for (int i = 3; i < n + 1; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) * (k - 1);
        }
        return dp[n];
    }

}
