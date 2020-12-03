package com.cuiyp.demo.leetcode.sort;

public class MergerSort {

    /**
     * 1. 排序 L...R 上数字有序
     * 2. 任务分而治之，保证最小范围 有序
     * 3. 小范围合并 交换
     * process -> f(arr,L M, R)
     * merger-> f(arr,L M, R)
     */

    public static void process(int[] arr, int begin, int end) {
        if (arr == null || arr.length < 1) throw new RuntimeException();
        if (begin == end) return;
        int mid = begin + ((end - begin) >> 1);
        process(arr, begin, mid);
        process(arr, mid + 1, end);
        merger(arr, begin, mid, end);
    }

    public static void merger(int[] arr, int left, int mid, int right) {
        int[] help = new int[(right - left) + 1];
        int p = 0;
        int l = 0;
        int r = mid + 1;
        while(l<=mid && r<=right){
            help[p++] = arr[l] <= arr[r] ?  arr[l++] : arr[r++];
        }
        while(l<=mid){
            help[p++] = arr[l++];
        }
        while(r<=mid){
            help[p++] = arr[r++];
        }
    }

}
