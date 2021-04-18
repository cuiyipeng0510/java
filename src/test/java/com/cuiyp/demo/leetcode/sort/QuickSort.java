package com.cuiyp.demo.leetcode.sort;

import com.cuiyp.demo.utils.ArrayUtils;

/**
 * 快速排序
 * 1. 设定分界值 R
 * 2. 指定范围有序， R 左边数字都比R小，右边数字都比R 大
 * 3. 左，右可以选定一个数 重复步骤 2
 */
public class QuickSort {


    public static int partiotion(int[] arr, int L, int R) {
        if (L > R) {
            return -1;
        }
        if (L == R) {
            return L;
        }
        int left = L - 1;
        int index = L;
        while (index < R) {
            if (arr[index] <= arr[R]) {
                ArrayUtils.swap(arr, index, ++left);
            }
            index++;
        }
        ArrayUtils.swap(arr, ++left, R);
        return left;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    public static void process(int[] arr, int L, int R) {
        if (L >= R) return;
        int partiotion = partiotion(arr, L, R);
        process(arr, L, partiotion - 1);
        process(arr, partiotion + 1, R);
    }

    public static void main(String[] args) {
        int[] arr = ArrayUtils.generateRandomArray(10,10);
        process(arr, 0, arr.length-1);
        System.out.println();
    }
}
