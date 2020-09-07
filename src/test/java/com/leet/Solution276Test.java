package com.leet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Solution276Test {

    @Test
    void numWays() {
        Solution276 solution276 = new Solution276();
        assertEquals(solution276.numWays2(4, 5), solution276.numWays(4, 5));
    }
}
