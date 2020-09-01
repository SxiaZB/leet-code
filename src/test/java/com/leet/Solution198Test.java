package com.leet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Solution198Test {

    @Test
    void rob() {
        Solution198 solution198 = new Solution198();
        int[] a = {1, 2, 3, 1};
        int[] b = {2,7,9,3,1};
        assertEquals(4, solution198.rob(a));
        assertEquals(12, solution198.rob(b));
    }
}
