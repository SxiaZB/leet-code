package com.leet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Solution209Test {

    @Test
    void minSubArrayLen() {
        int s = 7;
        int[] nums = {2, 3, 1, 2, 4, 3};
        Solution209 solution209 = new Solution209();
        int res = solution209.minSubArrayLen(s, nums);
        assertEquals(2, res);
    }
}
