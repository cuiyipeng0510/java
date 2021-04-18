package com.cuiyp.demo.leetcode.sort;

import com.cuiyp.demo.utils.ArrayUtils;

/**
 * 1. 0 ~ N-1 最大的数后移，直到第一次循环比较完毕
 * 2. 0 ~ N-2 最大的数后移
 */
public class BubbleSort {

    public static void bubbleSort(int[] arr) {
        if(arr == null || arr.length == 1) return;
        // 外层控制循环范围
        for (int i = arr.length - 1; i > 0  ; i--) {
            //内层 循环比较前后两个数大小并替换
            for (int j = 0; j < i; j++) {
                if(arr[j] > arr[j + 1]) ArrayUtils.swap(arr, j, j + 1);
            }
        }
    }
}
