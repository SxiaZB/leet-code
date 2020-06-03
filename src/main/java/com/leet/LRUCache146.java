package com.leet;

import java.util.HashMap;
import java.util.Map;

public class LRUCache146 {

    class DoubleLink {
        private DoubleLink prev;
        private DoubleLink next;

        private int key;

        private int val;

        public DoubleLink(int key, int val, DoubleLink prev, DoubleLink next) {
            this.key = key;
            this.val = val;
            this.prev = prev;
            this.next = next;
        }

        public DoubleLink getPrev() {
            return prev;
        }

        public void setPrev(DoubleLink prev) {
            this.prev = prev;
        }

        public DoubleLink getNext() {
            return next;
        }

        public void setNext(DoubleLink next) {
            this.next = next;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }

    private DoubleLink head;

    private DoubleLink tail;

    private Map<Integer, DoubleLink> cacheMap;

    private int capacity;

    public LRUCache146(int capacity) {
        if (capacity < 1) capacity = 0;
        this.capacity = capacity;
        cacheMap = new HashMap<>(capacity);
    }

    public int get(int key) {
        if (cacheMap.containsKey(key)) {
            DoubleLink now = cacheMap.get(key);
            if (now != head) {
                DoubleLink prev = now.getPrev();
                DoubleLink next = now.getNext();
                now.setPrev(null);
                now.setNext(head);
                head.setPrev(now);
                head = now;

                prev.setNext(next);
                if (next != null) {
                    next.setPrev(prev);
                } else {
                    tail = prev;
                }

            }
            return now.getVal();
        }
        return -1;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        if (cacheMap.containsKey(key)) {
            DoubleLink now = cacheMap.get(key);
            now.setVal(value);
            DoubleLink prev = now.getPrev();
            DoubleLink next = now.getNext();
            if (now == head) {
                return;
            }
            now.setPrev(null);
            now.setNext(head);
            if (head != null) {
                head.setPrev(now);
            }
            head = now;
            cacheMap.put(key, now);

            if (prev != null) {
                prev.setNext(next);
            }

            if (next != null) {
                next.setPrev(prev);
            } else {
                tail = prev;
            }
        } else {
            if (cacheMap.size() == capacity) {
                cacheMap.remove(tail.getKey());
                DoubleLink now = new DoubleLink(key, value, null, head);
                cacheMap.put(key, now);
                head.setPrev(now);
                head = now;
                DoubleLink prev = tail.getPrev();
                if (prev != null) {
                    prev.setNext(null);
                }
                tail.setPrev(null);
                tail = prev;
            } else {
                DoubleLink now = new DoubleLink(key, value, null, head);
                cacheMap.put(key, now);
                if (head != null) {
                    head.setPrev(now);
                }
                head = now;
                if (tail == null) {
                    tail = now;
                }
            }
        }
    }
}
