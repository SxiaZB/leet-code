package com.leet;

public class Solution83 {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode now = head;
        ListNode pre = null;
        while (now != null) {
            if (pre != null && pre.val == now.val) {
                now = now.next;
                pre.next = now;
            } else {
                pre = now;
                now = now.next;
            }
        }
        return head;
    }

    public class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
