package com.leet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Solution862Test {

    @Test
    void shortestSubarray() {
        int k = 167;
        int[] a = {84,-37,32,40,95};
        Solution862 solution862 = new Solution862();
        int res = solution862.shortestSubarray(a, k);
        assertEquals(3, res);
    }
}
