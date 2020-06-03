package com.leet;

/**
 * @link: https://leetcode-cn.com/problems/design-hashmap/
 */
public class MyHashMap {

    class Node {
        int key;
        int val;
        Node next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private final int nodesLength = 8;

    private Node[] nodes;

    /**
     * Initialize your data structure here.
     */
    public MyHashMap() {
        nodes = new Node[nodesLength];
    }

    /**
     * value will always be non-negative.
     */
    public void put(int key, int value) {
        int index = key % nodesLength;
        Node previous = null;
        Node now = nodes[index];
        while (true) {
            if (now == null || now.key > key) {
                Node newNode = new Node(key, value);
                if (previous == null) {
                    newNode.next = now;
                    nodes[index] = newNode;
                } else {
                    Node next = previous.next;
                    previous.next = newNode;
                    newNode.next = next;
                }
                return;
            } else if (now.key == key) {
                now.val = value;
                return;
            }
            previous = now;
            now = now.next;
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
     */
    public int get(int key) {
        int index = key % nodesLength;
        Node node = nodes[index];
        while (node != null) {
            if (node.key == key) {
                return node.val;
            } else if (node.key > key) {
                break;
            }
            node = node.next;
        }
        return -1;
    }

    /**
     * Removes the mapping of the specified value key if this map contains a mapping for the key
     */
    public void remove(int key) {
        int index = key % nodesLength;
        Node previous = null;
        Node now = nodes[index];
        while (now != null) {
            if (now.key == key) {
                if (previous == null) {
                    nodes[index] = null;
                } else {
                    previous.next = now.next;
                }
                now.next = null;
                return;
            } else if (now.key > key) {
                return;
            }
            previous = now;
            now = now.next;
        }
    }
}
