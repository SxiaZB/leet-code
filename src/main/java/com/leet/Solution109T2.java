package com.leet;

import java.util.HashMap;
import java.util.Map;

public class Solution109T2 {
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }
        int length = 0;
        Map<Integer, TreeNode> treeNodeMap = new HashMap<>();
        ListNode node = head;
        while (node != null) {
            treeNodeMap.put(length, new TreeNode(node.val));
            length++;
            node = node.next;
        }
        return helper(head, length, length / 2 + 1);
    }

    private TreeNode helper(ListNode head, int nodeLength, int rootIndex) {
        if (nodeLength <= 0 || rootIndex > nodeLength) {
            return null;
        }
        ListNode tmp = head;
        int index = 1;
        while (index < rootIndex) {
            tmp = tmp.next;
            index++;
        }
        TreeNode node = new TreeNode(tmp.val);
        node.left = helper(head, index - 1, (index - 1) / 2 + 1);
        node.right = helper(tmp.next, nodeLength - rootIndex, (nodeLength - rootIndex) / 2 + 1);
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
