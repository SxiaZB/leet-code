package com.leet;

public class Solution190 {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int res = 0;
        //固定长度
        for (int i = 0; i < 32; i++) {
            res = (res << 1) | (n & 1);
            n = n >> 1;
        }
        return res;
    }
}
