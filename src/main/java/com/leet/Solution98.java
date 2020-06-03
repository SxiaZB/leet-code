package com.leet;

import java.util.ArrayList;
import java.util.List;

public class Solution98 {
    // public boolean isValidBST(TreeNode root) {
    //     return recursiveIsValidBST(root,null,null);
    // }

    // //从下往上遍历，判断当前节点的值是否满足以下规律：
    // //如果该节点是父节点的左节点，并且父节点是祖父节点的左节点，此时需要满足该节点小于父节点；
    // //如果该节点是父节点的右节点，并且父节点是祖父节点的左节点，此时需要满足该节点大于父节点且小于祖父节点；
    // //如果该节点是父节点的左节点，并且父节点是祖父节点的右节点，此时需要满足该节点小于父节点且大于祖父节点；
    // //如果该节点是父节点的右节点，并且父节点是祖父节点的右节点，此时需要满足该节点大于父节点；
    // //如果该节点的父节点或祖父节点为空，则认为满足为空的那部分规律。
    // public boolean recursiveIsValidBST(TreeNode node,Integer low,Integer high){
    //     if(node==null){
    //         return true;
    //     }
    //     int now = node.val;
    //     if(low!=null&&low>=now){
    //         return false;
    //     }
    //     if(high!=null&&high<=now){
    //         return false;
    //     }
    //     boolean left = recursiveIsValidBST(node.left,low,now);
    //     if(!left){
    //         return false;
    //     }
    //     boolean right = recursiveIsValidBST(node.right,now,high);
    //     if(!right){
    //         return false;
    //     }
    //     return true;
    // }


    public boolean isValidBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inOrderTraversal(root, list);
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            if (list.get(i) >= list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    //利用二叉搜索树的中序遍历是从小到大的顺序，左>中>右，递归到左节点的最末端，将左节点添加到list后，再添加本身，再遍历右节点
    public void inOrderTraversal(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, list);
        list.add(node.val);
        inOrderTraversal(node.right, list);
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
