package com.leet;

import java.util.Arrays;

/**
 * @link：https://leetcode-cn.com/classic/problems/kth-smallest-element-in-a-sorted-matrix/description/
 */
public class Solution378 {
/*    //很low的做法
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length - 1;
        int[] nums = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nums[i * n + j] = matrix[i][j];
            }
        }
        Arrays.sort(nums);
        return nums[k - 1];
    }*/

    /**
     * 官方二分查找的办法
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0];
        int right = matrix[n - 1][n - 1];
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (check(matrix, mid, k, n)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public boolean check(int[][] matrix, int mid, int k, int n) {
        int i = n - 1;
        int j = 0;
        int num = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] <= mid) {
                num += i + 1;
                j++;
            } else {
                i--;
            }
        }
        return num >= k;
    }

}
