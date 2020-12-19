package com.cuiyp.demo.leetcode.sort;

import com.cuiyp.demo.leetcode.utils.ArrayUtils;

/**
 * 选择排序
 * 1. 0 ~ N-1 找最小的数下标 与 0 位置数字交换
 * 2. 1 ~ N-1 找最小的数下标 与 1 位置数字交换
 * 3. 2 ~ N-1 找最小的数下标 与 2 位置数字交换
 * ......
 */
public class SelectionSort {

    public static void selectSort(int[] arr) {
        if (arr == null || arr.length == 1) return;
        for (int i = 0; i < arr.length; i++) {
            int mixIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                // 查找最小数下标
                mixIndex = arr[i] > arr[j] ? j : mixIndex;
            }
            // 最小数与 i 位置交换
            ArrayUtils.swap(arr, i, mixIndex);
        }
    }
}
