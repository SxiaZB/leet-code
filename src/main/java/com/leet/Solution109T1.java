package com.leet;

public class Solution109T1 {

    ListNode c;

    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }
        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }
        c = head;
        return helper(0, length - 1);
    }

    private TreeNode helper(int s, int e) {
        if (s > e) {
            return null;
        }
        int m = s + ((e - s + 1) >> 1);
        TreeNode left = helper(s, m - 1);
        TreeNode node = new TreeNode(c.val);
        node.left = left;
        c = c.next;
        node.right = helper(m + 1, e);
        return node;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
