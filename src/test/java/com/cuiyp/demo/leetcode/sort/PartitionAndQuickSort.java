package com.cuiyp.demo.leetcode.sort;

import com.cuiyp.demo.utils.ArrayUtils;

public class PartitionAndQuickSort {

    // 荷兰国旗问题，三色区域算法
    // （L....R）上边的数
    // 1. num < 区域
    // 2. num = 区域
    // 3. num > 区域

    public static void partition(int[] arr, int l, int r){
        int less = l - 1;
        int index = l;
        int max = r;
        while(l < r){
            if(arr[index] == arr[r]){
                index++;
            } else if(arr[index] < arr[r]){
                ArrayUtils.swap(arr, index++, ++less);
            }else {
                ArrayUtils.swap(arr, index, --max);
            }
        }
        ArrayUtils.swap(arr, max, r);
    }

}
