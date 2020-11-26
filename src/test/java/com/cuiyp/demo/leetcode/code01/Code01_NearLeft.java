package com.cuiyp.demo.leetcode.code01;

import java.util.Arrays;

public class Code01_NearLeft {
    public static void main(String[] args) {
        findNearLeft(new int[2], 2);
    }

    // 找出数组arr上 满足 ? >= value 的最左位置
    public static int findNearLeft(int[] arr, int value) {
        int index = -1;
        int l = 0;
        int r = arr.length - 1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if (arr[mid] >= value) {
                index = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return index;
    }

    public static int test(int[] arr, int value){
        Arrays.sort(arr);
        for (int i = 0; i <arr.length; i++) {
            if(arr[i] >= value) return  i;
        }
        return -1;
    }

    public static int[] generateRandomArray(int maxSize, int maxVaule) {
        int[] arr = new int[(int)Math.random() * (maxSize + 1)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * (maxVaule + 1)) - (int)(Math.random() * maxVaule);
        }
        return arr;
    }
}
