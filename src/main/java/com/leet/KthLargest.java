package com.leet;

public class KthLargest {

    int[] heap;

    //heap数组中实际存放的元素数量
    int size = 0;

    public int add(int val) {
        if (size < heap.length) {
            size++;
            heap[size - 1] = val;

            if (size == heap.length) {
                //构建小顶堆
                makeMinHeap();
            }

        } else {
            if (heap[0] < val) {
                //替换堆顶元素
                heap[0] = val;
                minHeapFixdown(0);
            }
        }
        return heap[0];
    }

    /**
     * 堆化heap数组，建立最小堆
     */
    private void makeMinHeap() {
        int length = heap.length;
        //第一个非叶子节点是(length / 2) - 1
        for (int i = (length / 2) - 1; i >= 0; i--) {
            minHeapFixdown(i);
        }
    }

    /**
     * 从i节点开始调整, i节点的子节点为 2*i+1, 2*i+2
     *
     * @param i 第i个节点
     */
    public void minHeapFixdown(int i) {
        int temp = heap[i];
        //子节点是多少？i节点的子节点为 2*i+1, 2*i+2
        int subLeft = 2 * i + 1;
        while (subLeft < heap.length) {
            int subRight = subLeft + 1;
            if (subRight < heap.length && heap[subLeft] > heap[subRight]) {
                subLeft++;
            }

            if (heap[subLeft] >= heap[i]) {
                break;
            }
            heap[i] = heap[subLeft];
            heap[subLeft] = temp;

            i = subLeft;
            subLeft = 2 * i + 1;
        }

    }

    public int findKthLargest(int[] nums, int k) {
        heap = new int[k];
        for (int num : nums) {
            add(num);
        }
        return heap[0];
    }

}
