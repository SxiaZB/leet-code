package com.leet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @link: https://leetcode-cn.com/problems/coin-bonus/solution/di-yi-ci-xie-xian-duan-shu-dai-ma-cong-meng-bi-dao/
 */
public class SolutionLCP05 {
    private static int mod = 1000000007;
        private int id;
        private HashMap<Integer, List<Integer>> p;
        private HashMap<Integer, Integer> l, r;

        public int[] bonus(int n, int[][] leadership, int[][] operations) {
            p = new HashMap<>(n);
            for (int i = 0; i < leadership.length; i++) {
                int[] l = leadership[i];
                if (!p.containsKey(l[0])) {
                    p.put(l[0], new ArrayList<Integer>());
                }
                List<Integer> list = p.get(l[0]);
                list.add(l[1]);
            }
            id = -1;
            l = new HashMap<>();
            r = new HashMap<>();
            dfs(1);
            SegmentTree segmentTree = new SegmentTree(n, mod);
            List<Long> res = new ArrayList<>();
            for (int[] operation : operations) {
                if (operation[0] == 1) {
                    segmentTree.update(0, 0, n - 1, l.get(operation[1]), l.get(operation[1]), operation[2]);
                } else if (operation[0] == 2) {
                    segmentTree.update(0, 0, n - 1, l.get(operation[1]), r.get(operation[1]), operation[2]);
                } else {
                    res.add(segmentTree.query(0, 0, n - 1, l.get(operation[1]), r.get(operation[1])) % mod);
                }
            }
            return res.stream().mapToInt(Long::intValue).toArray();
        }

        private void dfs(int leader) {
            id++;
            l.put(leader, id);
            List<Integer> list = p.get(leader);
            if (list != null) {
                for (Integer pos : list) {
                    dfs(pos);
                }
            }
        r.put(leader, id);
    }

    class SegmentTree {

        private long[] tree;
        private long[] lazy;
        private int mod;

        public SegmentTree(int n, int mod) {
            this.tree = new long[n * 4];
            this.lazy = new long[n * 4];
            this.mod = mod;
        }

        public long query(int pos, int left, int right, int curLeft, int curRight) {
            if (right < curLeft || left > curRight) {
                return 0;
            }
            pushDown(pos, left, right);
            if (curLeft <= left && right <= curRight) {
                return this.tree[pos];
            }
            int mid = (left + right) >> 1;
            long queryL = query(pos * 2 + 1, left, mid, curLeft, curRight);
            long queryR = query(pos * 2 + 2, mid + 1, right, curLeft, curRight);
            return queryL + queryR;
        }

        public void update(int pos, int left, int right, int curLeft, int curRight, int val) {
            pushDown(pos, left, right);
            if (right < curLeft || left > curRight) {
                return;
            }
            if (curLeft <= left && right <= curRight) {
                this.lazy[pos] = val;
                pushDown(pos, left, right);
                return;
            }
            int mid = (left + right) >> 1;
            update(pos * 2 + 1, left, mid, curLeft, curRight, val);
            update(pos * 2 + 2, mid + 1, right, curLeft, curRight, val);
            this.tree[pos] = (this.tree[pos * 2 + 1] + this.tree[pos * 2 + 2]) % this.mod;
        }

        private void pushDown(int pos, int left, int right) {
            if (this.lazy[pos] == 0) {
                return;
            }
            this.tree[pos] += this.lazy[pos] * (right - left + 1);
            if (left < right) {
                this.lazy[pos * 2 + 1] += this.lazy[pos];
                this.lazy[pos * 2 + 2] += this.lazy[pos];
            }
            this.lazy[pos] = 0;
        }
    }
}
