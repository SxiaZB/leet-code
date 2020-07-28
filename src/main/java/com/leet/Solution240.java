package com.leet;

/**
 * @link: https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof/
 */
public class Solution240 {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int h = matrix.length - 1;
        int l = matrix[0].length - 1;

        int i = h, j = 0;
        while (j <= l && i >= 0) {
            int now = matrix[i][j];
            if (now == target) {
                return true;
            } else if (now > target) {
                i--;
            } else {
                j++;
            }
        }
        return false;
    }
}
