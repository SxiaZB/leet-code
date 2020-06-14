package com.leet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @link: https://leetcode-cn.com/problems/coin-bonus/solution/zhuan-huan-wei-xian-duan-shu-wen-ti-by-panpeng/
 */
public class SolutionLCP05T2 {
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
        Node segmentTree = new Node(0, n - 1);
        List<Long> res = new ArrayList<>();
        for (int[] operation : operations) {
            if (operation[0] == 1) {
                segmentTree.update(l.get(operation[1]), l.get(operation[1]), operation[2]);
            } else if (operation[0] == 2) {
                segmentTree.update(l.get(operation[1]), r.get(operation[1]), operation[2]);
            } else {
                res.add(segmentTree.query(l.get(operation[1]), r.get(operation[1]), 0));
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

    class Node {
        long mod = 1000000007;
        Node l, r;
        int s, e, m;
        long sum, val;
        boolean tag;

        public Node(int s, int e) {
            this.s = s;
            this.e = e;
            this.m = (s + e) >> 1;
            this.sum = 0;
            this.val = 0;
            this.tag = true;
        }

        private Node getL() {
            if (this.s == this.e) {
                return null;
            }
            if (this.l == null) {
                this.l = new Node(s, m);
            }
            return this.l;
        }

        private Node getR() {
            if (this.s == this.e) {
                return null;
            }
            if (this.r == null) {
                this.r = new Node(this.m + 1, this.e);
            }
            return this.r;
        }

        public void update(int start, int end, int newVal) {
            this.sum = (this.sum + newVal * (end - start + 1)) % mod;
            if (this.s == start && this.e == end) {
                val += newVal;
                return;
            }
            if (this.tag) {
                Node nodeL = this.getL();
                if (nodeL != null) {
                    nodeL.update(this.s, this.m, 0);
                }
                Node nodeR = this.getR();
                if (nodeR != null) {
                    nodeR.update(this.m + 1, this.e, 0);
                }
                this.tag = false;
            }
            if (end <= this.m) {
                Node nodeL = this.getL();
                if (nodeL != null) {
                    nodeL.update(start, end, newVal);
                }
            } else if (start >= this.m + 1) {
                Node nodeR = this.getR();
                if (nodeR != null) {
                    nodeR.update(start, end, newVal);
                }
            } else {
                Node nodeL = this.getL();
                if (nodeL != null) {
                    nodeL.update(start, m, newVal);
                }
                Node nodeR = this.getR();
                if (nodeR != null) {
                    nodeR.update(m + 1, end, newVal);
                }
            }
        }

        public long query(int start, int end, long add) {
            if (this.s == start && this.e == end) {
                return (this.sum + add * (end - start + 1)) % mod;
            }
            add += this.val;

            if (this.tag) {
                return (add * (end - start + 1)) % mod;
            }

            if (end <= this.m) {
                Node nodeL = this.getL();
                if (nodeL != null) {
                    return nodeL.query(start, end, add);
                }
            }
            if (start >= this.m + 1) {
                Node nodeR = this.getR();
                if (nodeR != null) {
                    return nodeR.query(start, end, add);
                }
            }
            long sumL = 0;
            Node nodeL = this.getL();
            if (nodeL != null) {
                sumL = nodeL.query(start, this.m, add);
            }
            long sumR = 0;
            Node nodeR = this.getR();
            if (nodeR != null) {
                sumR = nodeR.query(this.m + 1, end, add);
            }
            return (sumL + sumR) % mod;
        }
    }
}
