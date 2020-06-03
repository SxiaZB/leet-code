package com.leet;

import java.util.HashMap;
import java.util.Map;

public class Solution110T2 {
    public boolean isBalanced(TreeNode root) {
        Map<String, Integer> height = new HashMap<>(3);
        return helper(root, height);
    }

    private boolean helper(TreeNode node, Map<String, Integer> height) {
        if (node == null) {
            height.put("now", 0);
            return true;
        }
        if (!helper(node.left, height)) {
            return false;
        }
        height.put("left", height.get("now"));
        Integer left = height.get("left");
        if (!helper(node.right, height)) {
            return false;
        }
        height.put("right", height.get("now"));
        Integer right = height.get("right");
        if (Math.abs(left - right) > 1) {
            return false;
        }
        height.put("now", Math.max(left, right) + 1);
        return true;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
