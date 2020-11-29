package com.cuiyp.demo.leetcode.queue;

public class RingArray {
    /**
     * 使用数组实现一个环形buffer，队列
     * 1. 队列先进先出
     * 2. 数组固定大小
     * push
     * poll
     * size
     * limit
     */
    public class ArrayQueue {
        private int[] arr;
        private int pushIndex;
        private int popIndex;
        private int size;
        private final int limit;

        public ArrayQueue(int limit) {
            arr = new int[limit];
            this.pushIndex = 0;
            this.popIndex = 0;
            this.size = 0;
            this.limit = limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException();
            }
            arr[pushIndex] = value;
            size++;
            pushIndex = getNextIndex(pushIndex);
        }

        public int pop() {
            if (size == 0) {
                throw new RuntimeException();
            }
            int pop = arr[popIndex];
            popIndex = getNextIndex(popIndex);
            size--;
            return pop;
        }

        private int getNextIndex(int index) {
            return (index < limit - 1) ? index + 1 : 0;
        }
    }


}
