package com.leet;

import java.util.Deque;
import java.util.LinkedList;


public class Solution862 {

    //    /**
//     * 超时了
//     */
//    public int shortestSubarray(int[] A, int K) {
//        int res = -1;
//        for (int i = 0; i < A.length; ++i) {
//            if (A[i] >= K) {
//                return 1;
//            }
//            int sum = A[i];
//            int j = i + 1;
//            for (; j < A.length; ++j) {
//                int size = j - i + 1;
//                if (sum <= 0 || (res != -1 && size >= res)) {
//                    break;
//                }
//                sum += A[j];
//                if (sum >= K) {
//                    res = size;
//                    break;
//                }
//            }
//        }
//        return res;
//    }
    public int shortestSubarray(int[] A, int K) {
        int N = A.length;
        long[] P = new long[N + 1];
        for (int i = 0; i < N; ++i) {
            P[i + 1] = P[i] + (long) A[i];
        }
        int res = N + 1;
        Deque<Integer> queue = new LinkedList<>();

        for (int i = 0; i < P.length; ++i) {
            while (!queue.isEmpty() && P[i] <= P[queue.getLast()]) {
                queue.removeLast();
            }
            while (!queue.isEmpty() && P[i] >= P[queue.getFirst()] + K) {
                res = Math.min(res, i - queue.removeFirst());
            }

            queue.addLast(i);
        }
        return res < N + 1 ? res : -1;
    }
    class Solution {
        private static final int INIT_LENGTH = Integer.MAX_VALUE;

        public int shortestSubarray(int[] A, int K) {
            int minLength = INIT_LENGTH;
            int left = 0;
            int right = 0;
            int total = 0;
            while (right < A.length && total < K) {
                while (total < K && right < A.length) {
                    total += A[right];
                    right++;
                    if (total <= 0) {
                        left = right;
                        total = 0;
                        continue;
                    }
                    for (int track = right - 1; track >= left && A[track] < 0; track--) {
                        A[track - 1] += A[track];
                        A[track] = 0;
                    }
                }
                if (total < K) {
                    return minLength != INIT_LENGTH ? minLength : -1;
                }
                minLength = Math.min(right - left, minLength);
                while (total >= K) {
                    total -= A[left];
                    left++;
                }
                minLength = Math.min((right - left + 1), minLength);
            }
            return minLength;
        }
    }
}
