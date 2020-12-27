package com.cuiyp.demo.leetcode.utils;

public class ArrayUtils {
    /**
     * 对数器
     * 1. Math.random() [0, 1) 所有小数
     * 2. Math.random() * N [0, N) 所有小数
     * 3. (int)(Math.random() * N) [0, N-1] 所有正数
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] array = new int[(int) (Math.random() * (maxSize + 1))];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * maxValue);
        }
        return array;
    }

    /**
     * 数组元素交换
     * @param arr
     * @param index
     * @param value
     */
    public static void swap(int[] arr, int index, int value){
        if(index == value) return;
        arr[index] = arr[index] ^ arr[value];
        arr[value] = arr[index] ^ arr[value];
        arr[index] = arr[index] ^ arr[value];
    }
}
