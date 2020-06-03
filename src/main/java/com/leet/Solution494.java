package com.leet;

public class Solution494 {

    private int res = 0;

    /**
     * 思路就是把整个集合拆分成两个子集，Q表示整个集合，P表示正数子集，N表示负数子集， T表示目标和，用S(X)表示集合的求和函数，集合中均为非负数，N集合是指选中这部分元素作为负数子集。
     * <p>
     * S(P) - S(N) = T
     * S(P)−S(N)=T
     * <p>
     * S(P) + S(N) + S(P) - S(N) = T + S(P) + S(N)
     * S(P)+S(N)+S(P)−S(N)=T+S(P)+S(N)
     * <p>
     * 2S(P) = S(Q) + T
     * 2S(P)=S(Q)+T
     * <p>
     * 也就是：正数集的和的两倍 == 等于目标和 + 序列总和
     * <p>
     * 所以问题就转换成了，找到一个正数集P，其和的两倍等于目标和+序列总和
     */
    public int findTargetSumWays(int[] nums, int S) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if ((S + sum) % 2 != 0 || S > sum) {
            return 0;
        }
        S = (S + sum) / 2;
        int[] dp = new int[S + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            //当遍历第i个元素时，dp[x]表示在前i个元素的序列中，选择部分或者全部元素（不重复使用），能得到和为x的组合有多少种；
            // 如：前i=2个元素为1、2时，dp[0]=1、dp[1]=1、dp[2]=1、dp[3]=1
            //当遍历第i+1元素（num[i]）时，dp[x]等于dp[x-num[i]]的种数，加上前i个元素得到的dp[x]种数
            for (int j = S; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[S];
    }

    public int findTargetSumWays1(int[] nums, int S) {
        if (S > 1000) {
            return 0;
        }
        int[][] dp = new int[nums.length + 1][2001];
        //将1000当0点，设置初始值为1
        dp[0][1000] = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int sum = -1000; sum <= 1000; sum++) {
                if (dp[i][sum + 1000] > 0) {
                    dp[i + 1][sum + nums[i] + 1000] += dp[i][sum + 1000];
                    dp[i + 1][sum - nums[i] + 1000] += dp[i][sum + 1000];
                }
            }
        }
        return dp[nums.length][1000 + S];
    }

    public int findTargetSumWays2(int[] nums, int S) {
        recursive(nums, 0, 0, S);
        return res;
    }

    private void recursive(int[] nums, int now, int sum, int S) {
        if (now >= nums.length) {
            if (sum == S) {
                res = res + 1;
            }
            return;
        }
        recursive(nums, now + 1, sum + nums[now], S);
        recursive(nums, now + 1, sum - nums[now], S);
    }
}
