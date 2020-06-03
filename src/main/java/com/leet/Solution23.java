package com.leet;

public class Solution23 {
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode res = null;
        for (int i = 0; i < lists.length; i++) {
            res = mergeTwoLists(res, lists[i]);
        }
        return res;
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode l0, res;
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        if (l1.val < l2.val) {
            l0 = l1;
            l1 = l1.next;
        } else {
            l0 = l2;
            l2 = l2.next;
        }
        res = l0;

        while (l1 != null || l2 != null) {
            if (l1 == null) {
                l0.next = l2;
                break;
            } else if (l2 == null) {
                l0.next = l1;
                break;
            } else if (l1.val < l2.val) {
                l0.next = l1;
                l1 = l1.next;
                l0 = l0.next;
            } else {
                l0.next = l2;
                l2 = l2.next;
                l0 = l0.next;
            }
        }
        return res;
    }

    public class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
