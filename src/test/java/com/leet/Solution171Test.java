package com.leet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Solution171Test {

    @Test
    void titleToNumber() {
        Solution171 solution171 = new Solution171();
        assertEquals(1, solution171.titleToNumber("A"));
        assertEquals(26, solution171.titleToNumber("Z"));
        assertEquals(27, solution171.titleToNumber("AA"));
        assertEquals(701, solution171.titleToNumber("ZY"));
    }
}
