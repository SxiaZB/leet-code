package com.leet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Solution168Test {

    @Test
    void convertToTitle() {
        Solution168 solution168 = new Solution168();
        assertEquals(solution168.convertToTitle(1),"A");
        assertEquals(solution168.convertToTitle(26),"Z");
    }
}
