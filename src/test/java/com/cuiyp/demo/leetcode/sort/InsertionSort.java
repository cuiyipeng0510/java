package com.cuiyp.demo.leetcode.sort;

import com.cuiyp.demo.leetcode.utils.ArrayUtils;

/**
 * 插入排序
 * 1. 保证 0 ~ i 上有序
 */
public class InsertionSort {

    public static void instertionSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        for (int i = 1; i < arr.length; i++) {

            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > arr[j + 1]) ArrayUtils.swap(arr, j, j + 1);
            }
        }
    }
}
