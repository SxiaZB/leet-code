package com.leet;

/**
 * @link: https://leetcode-cn.com/problems/sum-of-two-integers/
 * @remark： https://blog.csdn.net/weixin_38190650/article/details/78384427
 */
public class Solution371 {
    public int getSum(int a, int b) {
        if (b == 0) {
            return a;
        }
        //在本位加输出中，若两个输入数相同则是0，不同则是1，用异或门^
        int sum = a ^ b;
        //在进位输出中，若输入的两个数都是1，才输出1，用与门&
        int c = (a & b) << 1;
        return getSum(sum, c);
    }
}
