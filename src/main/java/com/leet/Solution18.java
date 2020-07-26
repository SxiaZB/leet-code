package com.leet;

public class Solution18 {
    public ListNode deleteNode(ListNode head, int val) {
        ListNode now = head;
        ListNode pre = null;
        while (now != null) {
            if (val == now.val) {
                now = now.next;
                if (pre != null) {
                    pre.next = now;
                } else {
                    head = head.next;
                }
                break;
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
