package com.cuiyp.demo.leetcode.heap;

import com.cuiyp.demo.leetcode.utils.ArrayUtils;

public class MyHeap<T> {
    private int[] heap;
    private final int limit;
    private int heapSize;
    private Comparable<T> comparable;

    public MyHeap(Comparable<T> comparable, int limit) {
        this.heap = new int[limit];
        this.limit = limit;
        this.comparable = comparable;
    }

    private boolean isEmpty(){
        return limit == 0;
    }
    private boolean isFull(){
        return limit == heapSize;
    }
    private void push(int value) {
        if(isFull()) return;
        if(isEmpty()) {
            heap[heapSize++] = value;
            return;
        }
        heap[heapSize] = value;
        heapInsert(heapSize++);

    }

    private int pop() {
        if (isEmpty()) return -1;
        int cur = this.heap[0];
        ArrayUtils.swap(this.heap, 0, --heapSize);
        heapIfy(0);
        return cur;
    }

    /**
     * 取出数字时候，固定弹出 0位置数
     * 末尾数字覆盖 0 位置数字，数字下沉
     * index位置下沉，与子节点比较
     * 左孩子 i * 2 + 1
     * 右孩子 i * 2 + 2
     * @param index
     */
    private void heapIfy(int index){
        int left = index * 2 + 1;
        while(left < heapSize){
            // 判断是否有右孩子，并且比较左右孩子大小，返回较大孩子的下标
            int largest = left + 1 < heapSize && this.heap[left + 1] > this.heap[left] ? left + 1 : left;
            largest = this.heap[largest] > this.heap[index] ? largest : index;
            // 下表相等 说明index 当前位置数字大于或等于子树值
            if(largest == index) {
                break;
            }
            ArrayUtils.swap(heap, index, largest);
            // index位置来到 largest
            index = largest;
            //largest 位置左孩子
            left = index * 2 + 1;
        }
    }

    private void heapInsert(int index){
        // 找父节点 比较替换 （index - 1 ）/2
        while(this.heap[index] > this.heap[(index - 1) / 2]){
            ArrayUtils.swap(this.heap, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }
}
