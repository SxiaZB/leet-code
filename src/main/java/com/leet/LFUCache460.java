package com.leet;

import java.util.HashMap;
import java.util.Map;

public class LFUCache460 {

    private int minCount = 0;
    private Map<Integer, DoubleLink> countMap;

    private int capacity;
    private Map<Integer, DoubleLink> cacheMap;


    public LFUCache460(int capacity) {
        if (capacity < 1) capacity = 0;
        this.capacity = capacity;
        cacheMap = new HashMap<>(capacity);
        countMap = new HashMap<>();
    }

    public int get(int key) {
        if (cacheMap.containsKey(key)) {
            DoubleLink now = cacheMap.get(key);
            removeFromCurrentCountLink(now);
            updateMinCount(now.getCount());
            addToNextCountLink(now);
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
            removeFromCurrentCountLink(now);
            updateMinCount(now.getCount());
            addToNextCountLink(now);
        } else {
            if (cacheMap.size() == capacity) {
                DoubleLink minCountLinkHead = countMap.get(minCount);
                DoubleLink minCountLinkTail = minCountLinkHead.getPrev();
                cacheMap.remove(minCountLinkTail.key);
                if (minCountLinkHead == minCountLinkTail) {
                    countMap.remove(minCount);
                } else {
                    DoubleLink minCountLinkTailPrev = minCountLinkTail.getPrev();
                    minCountLinkTailPrev.setNext(minCountLinkHead);
                    minCountLinkHead.setPrev(minCountLinkTailPrev);
                }
            }
            DoubleLink now = new DoubleLink(key, value, null, null);
            addToNextCountLink(now);
            cacheMap.put(key, now);
            minCount = now.getCount();
        }
    }

    private void updateMinCount(int count) {
        if (count == minCount && countMap.get(count) == null) {
            minCount++;
        }
    }

    private void removeFromCurrentCountLink(DoubleLink now) {
        DoubleLink head = countMap.get(now.getCount());
        DoubleLink tail = head.getPrev();
        DoubleLink prev = now.getPrev();
        DoubleLink next = now.getNext();
        if (head == tail) {
            countMap.remove(now.getCount());
        } else {
            prev.setNext(next);
            next.setPrev(prev);
            if (head == now) {
                countMap.put(now.getCount(), next);
            }
        }
    }

    private void addToNextCountLink(DoubleLink now) {
        DoubleLink head = countMap.get(now.countAdd());
        if (head != null) {
            DoubleLink tail = head.getPrev();
            head.setPrev(now);
            tail.setNext(now);
            now.setPrev(tail);
            now.setNext(head);
        } else {
            now.setPrev(now);
            now.setNext(now);
        }
        countMap.put(now.getCount(), now);
    }

    class DoubleLink {
        private DoubleLink prev;
        private DoubleLink next;

        private int key;
        private int val;

        private int count;

        public DoubleLink(int key, int val, DoubleLink prev, DoubleLink next) {
            this.count = 0;
            this.key = key;
            this.val = val;
            this.prev = prev;
            this.next = next;
        }

        public int countAdd() {
            this.count += 1;
            return count;
        }

        public int getCount() {
            return count;
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
}
