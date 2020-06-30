package com.leet;

public class Solution862 {
        public int shortestSubarray(int[] A, int K) {
        int res = -1, sum = 0, l = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= K) {
                return 1;
            }
            sum += A[i];
            while ((sum - A[l]) >= K) {
                sum -= A[l];
                l++;
            }
            if (sum >= K) {
                int size = i - l + 1;
                res = res == -1 ? size : Math.min(size, res);
            }
        }
        return res;
    }

//    /**
//     * 超时了
//     */
//    public int shortestSubarray(int[] A, int K) {
//        int res = -1;
//        for (int i = 0; i < A.length; i++) {
//            if (A[i] >= K) {
//                return 1;
//            }
//            int sum = A[i];
//            int j = i + 1;
//            for (; j < A.length; j++) {
//                sum += A[j];
//                if (sum >= K) {
//                    break;
//                }
//            }
//            if (sum >= K) {
//                int size = j - i + 1;
//                res = res == -1 ? size : Math.min(size, res);
//            }
//        }
//        return res;
//    }
}
